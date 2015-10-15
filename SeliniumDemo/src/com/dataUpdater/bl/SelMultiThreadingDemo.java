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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataUpdater.bl.MSPUrlExtractor.DataExtractor;

public class SelMultiThreadingDemo {

	public static void main(String[] args) {
		WebDriver driver = null;
		Map<String,List<String>> m = new HashMap<>();
			m.put("set 1",Arrays.asList("[http://www.mysmartprice.com/computer/huawei-e8231-data-card-msf131432", "http://www.mysmartprice.com/computer/netgear-ac327u-wireless-data-card-msf131397"));
			m.put("set 2", Arrays.asList("http://www.mysmartprice.com/accessories/erocket-86945-gps-tracking-device-msf226105", "http://www.mysmartprice.com/accessories/mapmyindia-lx140ws-gps-navigation-device-msf213763","http://www.mysmartprice.com/computer/huawei-e8231-data-card-msf131432"));
			m.put("set 3", Arrays.asList("http://www.mysmartprice.com/appliance/v-guard-vg-400-voltage-stabilizer-msf190753","http://www.mysmartprice.com/appliance/v-guard-crystal-plus-voltage-stabilizer-msf190837"));
			SelMultiThreadingDemo mst = new SelMultiThreadingDemo();
			ExecutorService executor = Executors.newFixedThreadPool(3);
			  List<Future<String>> list = new ArrayList<Future<String>>();
			  for (Map.Entry<String, List<String>> entry : m.entrySet()) {
			  driver = new FirefoxDriver();	
						 Callable<String> callable = mst.new  DataExtractor(entry.getValue(),"demo",driver,entry.getKey());
							Future<String> future = executor.submit(callable);
				            //add Future to the list, we can get return value using Future
				            list.add(future);
			  }
			driver.close();		
					
				 
			
			  executor.shutdown();
	}
	
	class DataExtractor implements Callable{
		String query;
		 List<String> params;
		 JDBCConnection conn;
		 List<String> url;
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
	   	 public DataExtractor(List<String> url,String section,WebDriver driver,String id){
	   		 this.url  = url;
	   		 this.section = section;
	   		 this.driver = driver;
	   		 this.productid = id;
	   		params = new ArrayList<>();
	   		conn = JDBCConnection.getInstance();
	   		 
	   	 }
		@Override
		public Object call() throws Exception {
			
		this.url.forEach(s->{
			
		
			try{
				// get to the page
				driver.get(s);
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
					System.out.println(deliveryTime);
					
					// rating
					rating = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[2]/div[2]")).getAttribute("data-callout");
					// emi avaliable
					emi = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[3]/div[1]")).getText();
					// cod
					cod = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[3]/div[3]")).getAttribute("class");
					
				//	System.out.println("URL = "+vendorUrl);
					 price = driver.findElement(By.xpath("//*[@id='pricetable']/div["+i+"]/div[2]/div[5]/div[1]/div[1]")).getText();
					 									 
					//this.saveData(this.productid,this.section,(url.substring(url.lastIndexOf("/")+1,url.length())),vendorUrl,price,image,cod,deliveryTime,rating,emi);
					
				}
				
				}catch(Exception e){
					//e.printStackTrace();
					}
				
		
		});
		return cod;

	}
	}
}
