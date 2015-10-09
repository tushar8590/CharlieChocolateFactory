package com.dataUpdater.bl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;

public class MSPCatDataExtractor {

	 JDBCConnection conn;
	 
	 
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
		try {
			while(rs.next()){
				//System.out.println(rs.getString("url") + "  "+rs.getString("section"));
				DataExtractor de = 	this.new DataExtractor(rs.getString("url"),rs.getString("section"));
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			conn.closeConnection();
		}
	}
	
	class DataExtractor implements Callable{
		String query;
		 List<String> params;
		 JDBCConnection conn;
		 String url;
		 String section;
	   	 public DataExtractor(String baseUrl,String section){
	   		 this.url  = baseUrl;
	   		 this.section = section;
	   	 }
		@Override
		public Object call() throws Exception {
			// get to the page
			WebDriver driver = new FirefoxDriver();
			driver.get(this.url);
			WebElement radioButton = driver.findElement(By.id("online_stores"));
			radioButton.click();
			for(int i =3;i<=13;i++){
				System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[5]/div[2]/div")).getAttribute("data-url"));
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
