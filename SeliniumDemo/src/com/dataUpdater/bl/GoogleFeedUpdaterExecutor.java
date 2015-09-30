package com.dataUpdater.bl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataLoader.model.VendorProductData;
import com.dataUpdater.model.Product;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class GoogleFeedUpdaterExecutor {

	
	private List<Product> ProductList;

	private Map<Product,VendorProductData> itemMap;
	String section = "mobile_phones";
	
	JDBCConnection conn;
	
	//String[] vendors = {"amazon.in","ebay.in","snapdeal","fashionara","yepme","jabong","shopclues","paytm","infibeam"};
	String[] vendors = {"ebay.in"};
	
	static{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}

	
	public static void main(String[] args) {
	
		  ExecutorService executor = Executors.newFixedThreadPool(1);
		  List<Future<String>> list = new ArrayList<Future<String>>();
		System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog","fatal");
   	 //GoogleDataExtractor cfu = new GoogleDataExtractor();
		GoogleFeedUpdaterExecutor gfe = new GoogleFeedUpdaterExecutor();
   	 // populate the List of items to iterate
	 if(gfe.populateList()){
		 
		 Iterator<Product> itr = gfe.getItemsList().iterator();
		 
		 // for each product
		 while(itr.hasNext()){
			List<String> vendorsList = Arrays.asList(gfe.vendors);
			Product productName = itr.next();
			vendorsList.forEach(s->{
				
				
				Callable<String> callable = gfe.new  DataExtractor(productName,s);
				
	            Future<String> future = executor.submit(callable);
	            //add Future to the list, we can get return value using Future
	            list.add(future);
			});
/*	        for(Future<String> fut : list){
	            try {
	                //print the return value of Future, notice the output delay in console
	                // because Future.get() waits for task to get completed
	               // System.out.println(new Date()+ "::"+fut.get());
	            	fut.get();
	            } catch (InterruptedException | ExecutionException e) {
	                e.printStackTrace();
	            }
	        }*/
	        //shut down the executor service now
	       
		 }
		 executor.shutdown();
	 }
	}
	
	
	   public boolean populateList() {
	 		conn =   JDBCConnection.getInstance();
	 		boolean result = false;
	 		ProductList = new ArrayList<Product>();
	 		
	 		//List<String> param = new ArrayList<String>();
	 		String mastDataLoadQuery = SQLQueries.getShoesFKRecords;
	 			//	param.add(this.section);
	 				
	 		ResultSet rs = conn.executeQuery(mastDataLoadQuery,null);
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
	 		//	flag = conn.upsertData(sql, params);
	 			conn.closeConnection();
	 		}
	 		return flag;
	 	}


	     
    class DataExtractor implements Callable{
			 Product pm;
			 HtmlUnitDriver driver;
			 String vendor;
	    	 public DataExtractor(Product p,String vendor){
	    		this.pm = p; 
	    		this.vendor = vendor;
	    		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
	    		  driver.setJavascriptEnabled(true);
	    	 } 
	    	 
	    	 
			@Override
			public String call() throws Exception {
				 String  title = pm.getProductSearchString();
				 //title = "Puma Tazon 5 Black Running Shoes ";
				
				String search = title+ " + "+vendor;
				//System.out.println(search);
				
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
				    	CharSequence  ch = vendor;
				    	
				    	
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
				 max = Collections.max(scores);
				   
				 /*if(scores.size()>0){
				    max = GoogleFeedUpdaterExecutor.this.getMax(scores);
				   // System.out.println(cfu.getMax(scores));
				    }else{
				    	    	
				    	GoogleFeedUpdaterExecutor.this.logError(pm.getProductId(),vendor,"","Not found on Google");
				    //	continue;
				    }*/
				    
				    
				    iterator = urlScoreMap.entrySet().iterator();
				    List<String> urls = new ArrayList<String>();
				    while(iterator.hasNext()){
				    	Entry<String,Integer> entry = iterator.next();
				    	if(entry.getValue().equals(max))
				    	//System.out.println(entry.getKey());
				    		urls.add(entry.getKey());
				    }
				        
					System.out.println(urls);
				    
				    
					ShoePageDataExtractor td = new ShoePageDataExtractor(urls,pm, driver, "insert", vendor);
				   if(!td.processData()){ // if data not inserted for any of the vendor
					   //System.out.println("Deferring status for "+pm.getProductId());
					   JDBCConnection  conn = JDBCConnection.getInstance(); 
						List<String> params = new ArrayList<String>(); 
						params.add(pm.getProductId());
						String sql = SQLQueries.deferUpdateElecMultiVendor;
						//conn.upsertData(sql, params);
				   }
		
				   
				  //  driver.close();
				    //driver.quit();
			 
    			 
				return null;
			}
	    	 
	     }
}
