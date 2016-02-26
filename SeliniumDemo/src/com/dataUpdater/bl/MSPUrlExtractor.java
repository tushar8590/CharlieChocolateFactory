package com.dataUpdater.bl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.dataLoader.model.VendorProductData;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class MSPUrlExtractor {

	static{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	
	static Map<String,List<String>> urlMap;
	public static void main(String[] args) {
		JDBCConnection conn1 = JDBCConnection.getInstance();
		MSPUrlExtractor msp = new MSPUrlExtractor();
		ExecutorService executor = Executors.newFixedThreadPool(3);
		  List<Future<String>> list = new ArrayList<Future<String>>();
		urlMap = new HashMap<String,List<String>>();
		
		String fetchUrlQuery = SQLQueries.fetchAllUrl;
		
		ResultSet rs1 = conn1.executeQuery(fetchUrlQuery);
		List productUrl = new ArrayList();
		if(rs1!=null){
			try {
				while(rs1.next()){
					String url = rs1.getString("url");
					
					productUrl.add(url);
					}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String fetchQuery = SQLQueries.fetchMainCategoryMap;
		
		ResultSet rs = conn1.executeQuery(fetchQuery);
		Map<String,List<String>> urlMap = new HashMap<String,List<String>>();
		if(rs!=null){
			try {
				while(rs.next()){
					String cat = rs.getString("category");
					
					
					String first_url = rs.getString("first_page_url");
					String second_url =rs.getString("second_page_url");
					String number =rs.getString("total_pages");
					urlMap.put(cat, Arrays.asList(first_url,second_url,number));
					}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		urlMap.forEach((k,v)->{
			Callable<String> callable = msp.new  DataExtractor(v.get(0),v.get(1),v.get(2),k,productUrl);
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
		 List allExistingUrl;

		 JDBCConnection conn;
   	 public DataExtractor(String baseUrl,String otherUrl,String limit,String section, List allUrl){
   		this.baseUrl = baseUrl;
   		this.otherUrls = otherUrl;
   		this.section = section;
   		this.limit = Integer.parseInt(limit);
   		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
   		params = new ArrayList<>();
   		allExistingUrl = allUrl;
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
	        		productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[5]/div[2]/div[1]/div["+i+"]/div[2]/a")).getAttribute("href");
	        	else
	                productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div["+i+"]/div[2]/a")).getAttribute("href");
	        	
	        	if(!allExistingUrl.contains(productUrl))
	            this.saveData(productUrl, section);
	        }
	        
	        
	    // for the otherUrls    
	  for(int j = 2;j<=limit;j++){
		 
		  driver.get(otherUrls+j+".html");
	        for(int i = 4 ;i<=51;i++){
	        	if(section.equals("tablets"))
	        		productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[5]/div[2]/div[1]/div["+i+"]/div[2]/a")).getAttribute("href");
	        	else
	                productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div["+i+"]/div[2]/a")).getAttribute("href");
	        	if(!allExistingUrl.contains(productUrl))
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
		 params.add("elecaap");
		 params.add(url);
		 params.add(section);
		 params.add("i");
		 conn.upsertData(query, params); 
		 params.clear();
	   // System.out.println(url+" "+ section);
	}
   	 
	
	
	}
}
