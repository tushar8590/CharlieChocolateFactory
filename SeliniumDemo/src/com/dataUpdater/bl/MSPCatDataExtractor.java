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
	 Map<String,List<String>> urlMap;
	static String idBase = "";
	int count;
	private static Map<String,Map<String,List<String>>> mainMap;
	private Map<String,List<String>> subMap;
	private List<String> urlList;
	public static void main(String[] args) {
		MSPCatDataExtractor mspcatDe = new MSPCatDataExtractor();
		mspcatDe.getUrls();
		WebDriver driver = new FirefoxDriver();
		ExecutorService executor = Executors.newFixedThreadPool(6);
		  List<Future<String>> list = new ArrayList<Future<String>>();
			
			mainMap.forEach((k,v) ->{
				System.out.println(k+"  "+v);
				/*v.forEach((key,val) ->{
					val.forEach(url->{
						Callable<String> callable = mspcatDe.new DataExtractor(url, key,driver,idBase);
						Future<String> future = executor.submit(callable);
						 list.add(future);
					});
				}
			);*/
			});
			
		//  Callable<String> callable = mspcatDe.new DataExtractor("http://www.mysmartprice.com/mobile/samsung-galaxy-tab-3-neo-8gb-(wi-fi-3g)-msp4025", "mobiles");
			
            //add Future to the list, we can get return value using Future
           
            executor.shutdown();
		
		
	}
	
	/**
	 * Load the database urls
	 * @author Tushar
	 *
	 */
	public void getUrls(){
		conn = JDBCConnection.getInstance();
		//String query  = SQLQueries.getMspUrls;
		String mainQuery = "select distinct menu_level1 from msp_product_url";
		//String query = "SELECT DISTINCT section FROM msp_product_url WHERE  menu_level1 = 'mobiles' ORDER BY section;";
		ResultSet rs1 = conn.executeQuery(mainQuery, null);
		
		mainMap = new HashMap<>();
		
		
		try {
			while(rs1.next()){
				String subMenuQuery = "SELECT DISTINCT section FROM msp_product_url WHERE  menu_level1 = '"+rs1.getString("menu_level1")+"' ORDER BY section";
				// get the prodct corr to each section
				ResultSet rs = conn.executeQuery(subMenuQuery, null);
				urlMap = new HashMap<>();
				while(rs.next()){
						String getProductUrl = "select * from msp_product_url where section = '"+rs.getString("section")+"' LIMIT 3";
						ResultSet rsProductUrl = conn.executeQuery(getProductUrl, null);
						urlList = new ArrayList<>();
						while(rsProductUrl.next()){
							urlList.add(rsProductUrl.getString("url"));
							
						}
						urlMap.put(rs.getString("section"), urlList);
				}
				mainMap.put(rs1.getString("menu_level1"), urlMap);
			}
	
			
/*			urlMap.forEach((k,v) -> {
				idBase = "AC" + count;
				DataExtractor de = 	this.new DataExtractor(k,v,driver,idBase);
				count++;
				 try {
					de.call();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
*/		
			} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			//conn.closeConnection();
		}
	}
	
	class DataExtractor implements Callable{
		String query;
		 List<String> params;
		 JDBCConnection conn;
		 String url;
		 String section;
		 WebDriver driver ;
		 String vendorUrl;
		 String deliveryTime;
		 String emi;
		 String cod;
		 String rating;
		 String image;
		 String price;
		String productid;
	   	 public DataExtractor(String baseUrl,String section,WebDriver driver,String id){
	   		 this.url  = baseUrl;
	   		 this.section = section;
	   		 this.driver = driver;
	   		 this.productid = id;
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
			
			
			// image
			image = driver.findElement(By.xpath("//*[@id='mspSingleImg']")).getAttribute("src");
			
			for(int i =3;i<=13;i++){
				//System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[5]/div[2]/div")).getAttribute("data-url"));
				vendorUrl = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[5]/div[2]/div")).getAttribute("data-url");
				// delivery time
				//deliveryTime = driver.findElement(By.xpath("//*[@id='pricetable']/div[3]/div[2]/div[4]/div[1]")).getText();
				   deliveryTime = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[4]/div[1]")).getText();
				//System.out.println(deliveryTime);
				
				// rating
				rating = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[2]/div[2]")).getAttribute("data-callout");
				// emi avaliable
				emi = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[3]/div[1]")).getText();
				// cod
				cod = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[3]/div[3]")).getAttribute("class");
				
				//System.out.println("URL = "+vendorUrl);
				 price = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[5]/div[1]/div[1]")).getText();
				 									 
				this.saveData(this.productid,this.section,(url.substring(url.lastIndexOf("/")+1,url.length())),vendorUrl,price,image,cod,deliveryTime,rating,emi);
				
			}
			
			}catch(Exception e){
				//e.printStackTrace();
				}
			
			return null;
		}
		private void saveData(String ... data){
			 query = SQLQueries.insertMspProductData;
			 for(String s:data)
				 params.add(s);
			 conn.upsertData(query, params); 
			 params.clear();
			 query = SQLQueries.updateMSPUrlFlag;
			 params.add(url);
	          conn.upsertData(query, params); 
			 params.clear();
		   // System.out.println(url+" "+ section);
		}
	}
	
}
