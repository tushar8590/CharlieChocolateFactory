package com.dataUpdater.bl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;

import com.dataLoader.model.VendorProductData;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.dataUpdater.model.Product;


public class CategoryFeedUpdater {

	private List<Product> ProductList;
	//private List<VendorProductData> itemResults = new ArrayList<VendorProductData>();
	private Map<Product,VendorProductData> itemMap;
	String section = "mobile_phones";
	
	JDBCConnection conn;
	
	String[] vendors = {"homeshop18"};
	
	static{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	
	
	 public static void main(String ar[]){
		System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog","fatal");
	    	 CategoryFeedUpdater cfu = new CategoryFeedUpdater();
	    	 
	    	 
	    	 
	    	 if(cfu.populateList()){
	    		// Iterate each product
	    		 Iterator<Product> itr = cfu.getItemsList().iterator();
	    		 while(itr.hasNext()){
	    			 // now for each product search the data corresponding to each of 10 vendors
	    			 Product pm = itr.next();
	    			
	    				
	    				// search in google for the product for specific vendor
	    			//WebDriver driver = new FirefoxDriver();
	    			 for(int i = 0 ;i<cfu.vendors.length;i++){
	  				  HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
					  driver.setJavascriptEnabled(true);
					  
					  String  title = pm.getProductSearchString();
					 // title = "apple ipad 64GB";
					
					String search = title+ " + "+cfu.vendors[i] ;
					
					
					System.out.println(search);
					
					driver.get("https://www.google.co.in");
					driver.findElement(By.name("q")).sendKeys(search);
					driver.findElement(By.cssSelector("#tsf > div.tsf-p > div.jsb > center > input[type='submit']:nth-child(1)")).click();
					
					
					WebElement myDynamicElement = (new WebDriverWait(driver, 20))
				              .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
					 
					 List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

					    // this are all the links you like to visit
					 NavigableMap<String,Integer> urlScoreMap = new TreeMap<String,Integer>();
					    for (WebElement webElement : findElements)
					    {
					    	String strURL=webElement.getAttribute("href");
					       // print the urls
					    	//System.out.println(strURL);
					    	CharSequence  ch = cfu.vendors[i];
					    	
					    	
					    	//System.out.println(strURL);
				    	if(strURL.contains(ch)){
					    		// regex pattern matching
					    		String strArr[] = title.split(" ");
					    		
					    		int count =0;
					    		for(int k =0;k<strArr.length;k++){
					    			String pattern =strArr[k];
					    			
					    			Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
					    			;
					    			Matcher m = p.matcher(strURL);
					    			if(m.find())
					    			{
					    				count++;
					    			}
					    		}
					    		urlScoreMap.put(strURL, count);
					    		if(count>=3){
					    		
					    		break;
					    		}
					    		count = 0;
					    		
					    		
					    	}
				    	
					        
						}
					 
					    System.out.println(urlScoreMap);
					    Entry<String, Integer> lastEntry = urlScoreMap.lastEntry();
					    System.out.println(lastEntry);
					    
					    /*VendorDataUpdater vdu = VendorFactory.getInstance(cfu.vendors[i],lastEntry.getKey(),driver,"insert");
		    			vdu.processData();*/
					    
					    TestDataUpdater td = new TestDataUpdater(lastEntry.getKey(),pm, driver, "insert", cfu.vendors[i]);
					    td.processData();
					    // check the key value with highest count
					    
					    driver.close();
					    driver.quit();
	    			} 
	    			 
	    			 
	    		 }
	    	 }
	    	 
	    	 
	     }
	     
	     public boolean populateList() {
	 		conn =   JDBCConnection.getInstance();
	 		boolean result = false;
	 		ProductList = new ArrayList<Product>();
	 		
	 		String mastDataLoadQuery = SQLQueries.getMasterFeedDataForupdate;
	 		List<String> param = new ArrayList<String>();
	 				param.add(this.section);
	 				
	 		ResultSet rs = conn.executeQuery(mastDataLoadQuery,param);
	 		if(rs!=null){
	 			try {
	 				while(rs.next()){
	 						Product pm = new Product(rs.getString("product_id"),
	 						rs.getString("model"));
	 						ProductList.add(pm);
	 						
	 					}
	 				result = true;
	 			} catch (SQLException e) {
	 				e.printStackTrace();
	 				result = false;
	 			}
	 		}
	 		conn.closeConnection();
	 		return result;
	 		
	 	}
	     
	     public List<Product> getItemsList() {
	 		return ProductList;
	 	}
	
}
