package com.dataUpdater.bl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.SortedMap;
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
	
	String[] vendors = {"infibeam","amazon.in","homeshop18","croma","ebay.in","snapdeal","indiatimes","shopclues","rediff","themobileStore"};
	
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
	  				  HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
					  driver.setJavascriptEnabled(true);
					  
					  String  title = pm.getProductSearchString();
					// title = "Apple-iPhone-6 Gold-with-64 GB";
					
					String search = title+ " + "+cfu.vendors[i];
					
					
					System.out.println(search);
					
					driver.get("http://www.google.co.in");
					driver.findElement(By.name("q")).sendKeys(search);
					driver.findElement(By.cssSelector("#tsf > div.tsf-p > div.jsb > center > input[type='submit']:nth-child(1)")).click();
					
					
					WebElement myDynamicElement = (new WebDriverWait(driver, 20))
				              .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
					 
					 List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

					    // this are all the links you like to visit
					 Map<String,Integer> urlScoreMap = new TreeMap<String,Integer>();
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
					    		/*if(count>=3){
					    		
					    		break;
					    		}*/
					    		count = 0;
					    		
					    		
					    	}
				    	
					        
						}
					 
					    // run it for all the highest scores till u get the data 
					    //  To do that pass the 
					    
					    // get teh keys with max value
					    List<Integer> scores = new ArrayList<Integer>();
					    Iterator<Entry<String, Integer>> iterator = urlScoreMap.entrySet().iterator();
					    while(iterator.hasNext()){
					    	Entry<String,Integer> entry = iterator.next();
					    	scores.add(entry.getValue());
					    }
					    Collections.sort(scores,Collections.reverseOrder());
					    
					    Integer max = 0;
					    if(scores.size()>0){
					    max = cfu.getMax(scores);
					   // System.out.println(cfu.getMax(scores));
					    }else{
					    	    	
					    	cfu.logError(pm.getProductId(),cfu.vendors[i],"","Not found on Google");
					    	continue;
					    }
					    
					    
					    iterator = urlScoreMap.entrySet().iterator();
					    List<String> urls = new ArrayList<String>();
					    while(iterator.hasNext()){
					    	Entry<String,Integer> entry = iterator.next();
					    	if(entry.getValue().equals(max))
					    	//System.out.println(entry.getKey());
					    		urls.add(entry.getKey());
					    }
					    
					    
					   // Entry<String, Integer> lastEntry = urlScoreMap.lastEntry();
					    //System.out.println(lastEntry);
					    
					    /*VendorDataUpdater vdu = VendorFactory.getInstance(cfu.vendors[i],lastEntry.getKey(),driver,"insert");
		    			vdu.processData();*/
					    
					    TestDataUpdater td = new TestDataUpdater(urls,pm, driver, "insert", cfu.vendors[i]);
					   if(!td.processData()){ // if data not inserted for any of the vendor
						   System.out.println("Deferring status for "+pm.getProductId());
						   JDBCConnection  conn = JDBCConnection.getInstance(); 
							List<String> params = new ArrayList<String>(); 
							params.add(pm.getProductId());
							String sql = SQLQueries.deferUpdateElecMultiVendor;
							conn.upsertData(sql, params);
					   }
			
					   
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
	     
	     
	     
	     public Integer getMax(List<Integer> scores){
	    	
	    	 Integer max = scores.get(0);
	    	 for(Integer score:scores){
	    		 if(score>max){
	    			
	    			 max=score;
	    			 
	    		 }
	    	 }
	    	 return max;
	     }
	     
	     public boolean logError(String productId,String vendor,String url,String errorMsg){
	 		boolean flag = false;
	 		if(productId!=null){
	 			conn = JDBCConnection.getInstance(); 
	 			List<String> params = new ArrayList<String>(); 
	 			params.add(productId);
	 			params.add(vendor);
	 			params.add(url);
	 			params.add(errorMsg);
	 			String sql = SQLQueries.logElecUnmapped;
	 			flag = conn.upsertData(sql, params);
	 			conn.closeConnection();
	 		}
	 		return flag;
	 	}

	
}
