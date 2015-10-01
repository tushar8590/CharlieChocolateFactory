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

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class MSPUrlExtractor {

	static{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	
	static Map<String,List<String>> urlMap;
	public static void main(String[] args) {
		MSPUrlExtractor msp = new MSPUrlExtractor();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		  List<Future<String>> list = new ArrayList<Future<String>>();
		urlMap = new HashMap<String,List<String>>();
		urlMap.put("mobiles", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html#subcategory=mobile","http://www.mysmartprice.com/mobile/pricelist/pages/mobile-price-list-in-india-","76"));
		//urlMap.put("tablets", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/tablet-price-list-in-india.html#subcategory=tablet&property=1200652-2267506","http://www.mysmartprice.com/mobile/pricelist/pages/tablet-price-list-in-india-2.html"));
		//urlMap.put("smatwatches", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/smart-watches-price-list-in-india.html","http://www.mysmartprice.com/mobile/pricelist/pages/smart-watches-price-list-in-india-2.html"));
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
   	 public DataExtractor(String baseUrl,String otherUrl,String limit,String section){
   		this.baseUrl = baseUrl;
   		this.otherUrls = otherUrl;
   		this.section = section;
   		this.limit = Integer.parseInt(limit);
   		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
   		driver.setJavascriptEnabled(true);
   	 }
	@Override
	public Object call() throws Exception {
	System.out.println("Calling  for "+section+" "+ baseUrl +"  " +otherUrls); 
	   // get the base url data  
	        try{
	        driver.get(baseUrl);
	        System.out.println("M here");
	        for(int i = 4 ;i<=51;i++){
	            System.out.println("M here");
	            productUrl = driver.findElement(By.cssSelector("#msp_body > div > div.msplistleft > div.productlistmiddle > div.listitems_rd.info-items-5 > div.product-list > div:nth-child("+i+") > a")).getAttribute("href");
	            this.saveData(productUrl, section);
	        }
	        
	        }catch(Exception e){
	            e.printStackTrace();
	        } 
	        
	 /* for(int i = 2;i<=limit;i++){
	      
	  }*/
	    
	    driver.close();
		return null;
	}
	
	private void saveData(String url,String section){
	    System.out.println(url+" "+ section);
	}
   	 
	}
}
