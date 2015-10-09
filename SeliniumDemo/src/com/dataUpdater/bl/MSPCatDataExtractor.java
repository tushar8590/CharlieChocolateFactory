package com.dataUpdater.bl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;

public class MSPCatDataExtractor {

	 JDBCConnection conn;
	 Map<String,String> urlMap;
	 
	public static void main(String[] args) {
		MSPCatDataExtractor mspcatDe = new MSPCatDataExtractor();
		mspcatDe.getUrls();
		/*ExecutorService executor = Executors.newFixedThreadPool(1);
		  List<Future<String>> list = new ArrayList<Future<String>>();
			Callable<String> callable = mspcatDe.new DataExtractor("http://www.mysmartprice.com/mobile/samsung-galaxy-tab-3-neo-8gb-(wi-fi-3g)-msp4025", "mobiles");
			Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
            executor.shutdown();*/
		
	}
	
	/**
	 * Load the database urls
	 * @author Tushar
	 *
	 */
	public void getUrls(){
		conn = JDBCConnection.getInstance();
		String query  = SQLQueries.getMspUrls;
		ResultSet rs = conn.executeQuery(query, null);
		WebDriver driver = new FirefoxDriver();
		urlMap = new HashMap<>();
		try {
			while(rs.next()){
				//System.out.println(rs.getString("url") + "  "+rs.getString("section"));
				urlMap.put(rs.getString("url"), rs.getString("section"));
				
			}
			urlMap.forEach((k,v) -> {
				DataExtractor de = 	this.new DataExtractor(k,v,driver);
				 try {
					de.call();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			driver.close();
			conn.closeConnection();
		}
	}
	
	class DataExtractor implements Callable{
		String query;
		 List<String> params;
		 JDBCConnection conn;
		 String url;
		 String section;
		 WebDriver driver ;
	   	 public DataExtractor(String baseUrl,String section,WebDriver driver){
	   		 this.url  = baseUrl;
	   		 this.section = section;
	   		 this.driver = driver;
	   		params = new ArrayList<>();
	   		conn = JDBCConnection.getInstance();
	   	 }
		@Override
		public Object call() throws Exception {
			try{
			// get to the page
			driver.get(this.url);
			try{
				WebElement radioButton = driver.findElement(By.xpath("//*[@id='online_stores']"));
			
			radioButton.click();
			}catch(Exception e){}
			String vendorUrl;
			for(int i =3;i<=13;i++){
				//System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[5]/div[2]/div")).getAttribute("data-url"));
				vendorUrl = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[5]/div[2]/div")).getAttribute("data-url");
				//System.out.println("URL = "+vendorUrl);
				this.saveData(vendorUrl);
			}
			
			}catch(Exception e){
				//e.printStackTrace();
				}
			
			return null;
		}
		private void saveData(String vendorUrl){
			 query = SQLQueries.insertMspVendorUrl;
			 params.add(url.substring(url.lastIndexOf("/")+1,url.length()));
			 params.add(vendorUrl);
			 params.add(this.section);
			 conn.upsertData(query, params); 
			 params.clear();
		   // System.out.println(url+" "+ section);
		}
	}
	
}
