package com.dataUpdater.bl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class MSPUrlExtractor {

	static{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	
	static Map<String,List<String>> urlMap;
	public static void main(String[] args) {
		MSPUrlExtractor msp = new MSPUrlExtractor();
		ExecutorService executor = Executors.newFixedThreadPool(3);
		  List<Future<String>> list = new ArrayList<Future<String>>();
		urlMap = new HashMap<String,List<String>>();
		urlMap.put("mobiles", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html#subcategory=mobile","http://www.mysmartprice.com/mobile/pricelist/pages/mobile-price-list-in-india-","76"));
		urlMap.put("tablets", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/tablet-price-list-in-india.html#subcategory=tablet&property=1200652-2267506","http://www.mysmartprice.com/mobile/pricelist/pages/tablet-price-list-in-india-2.html","11"));
		urlMap.put("smatwatches", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/smart-watches-price-list-in-india.html","http://www.mysmartprice.com/mobile/pricelist/pages/smart-watches-price-list-in-india-2.html","4"));
		urlMap.forEach((k,v)->{
			Callable<String> callable = msp.new  DataExtractor(v.get(0),v.get(1),v.get(2),k);
			Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
		});
		 executor.shutdown();
		
	}
	
	class DataExtractor implements Callable{
		String baseUrl;
		String otherUrls;
		int limit;
		String productUrl;
		HtmlUnitDriver driver;
		 String section;
		 String keyword; //2.html
		 String query;
		 List<String> params;
		 JDBCConnection conn;
   	 public DataExtractor(String baseUrl,String otherUrl,String limit,String section){
   		this.baseUrl = baseUrl;
   		this.otherUrls = otherUrl;
   		this.section = section;
   		this.limit = Integer.parseInt(limit);
   		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
   		params = new ArrayList<>();
   		conn = JDBCConnection.getInstance();
   		//driver.setJavascriptEnabled(true);
   	 }
	@Override
	public Object call() throws Exception {
	System.out.println("Calling  for "+section); 
	   // get the base url data  
	        try{
	        driver.get(baseUrl);
	        for(int i = 4 ;i<=51;i++){
	        	if(section.equals("tablets"))
	        		productUrl = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[1]/div[5]/div[1]/div[4]/div["+i+"]/div/a")).getAttribute("href");
	        	else
	                productUrl = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[1]/div[4]/div[1]/div[3]/div["+i+"]/div/a")).getAttribute("href");
	            this.saveData(productUrl, section);
	        }
	        
	        
	    // for the otherUrls    
	  for(int j = 2;j<=limit;j++){
		 
		  driver.get(otherUrls+j+".html");
	        for(int i = 4 ;i<=51;i++){
	        	if(section.equals("tablets"))
	        		productUrl = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[1]/div[4]/div[1]/div[4]/div["+i+"]/div/a")).getAttribute("href");
	        	else
	                productUrl = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[1]/div[4]/div[1]/div[3]/div["+i+"]/div/a")).getAttribute("href");
	            this.saveData(productUrl, section);
	        }
	  }
	
	  
	        }catch(Exception e){
	        	System.out.println("Section " + section);
	        	e.getMessage();
	            e.printStackTrace();
	        }  
	    driver.close();
		return null;
	}
	
	private void saveData(String url,String section){
		 query = SQLQueries.insertMspProductUrl;
		 params.add(url);
		 params.add(section);
		 conn.upsertData(query, params); 
		 params.clear();
	   // System.out.println(url+" "+ section);
	}
   	 
	}
}
