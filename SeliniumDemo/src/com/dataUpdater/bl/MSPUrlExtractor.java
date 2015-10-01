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
		urlMap.put("mobiles", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html#subcategory=mobile","http://www.mysmartprice.com/mobile/pricelist/pages/mobile-price-list-in-india-2.html"));
		urlMap.put("tablets", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/tablet-price-list-in-india.html#subcategory=tablet&property=1200652-2267506","http://www.mysmartprice.com/mobile/pricelist/pages/tablet-price-list-in-india-2.html"));
		urlMap.put("smatwatches", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/smart-watches-price-list-in-india.html","http://www.mysmartprice.com/mobile/pricelist/pages/smart-watches-price-list-in-india-2.html"));
		urlMap.forEach((k,v)->{
			Callable<String> callable = msp.new  DataExtractor(v.get(0),v.get(1),k);
			Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
		});
		 executor.shutdown();
		
	}
	
	class DataExtractor implements Callable{
		String baseUrl;
		String otherUrls;
		HtmlUnitDriver driver;
		 String section;
   	 public DataExtractor(String baseUrl,String otherUrl,String section){
   		this.baseUrl = baseUrl;
   		this.otherUrls = otherUrl;
   		this.section = section;
   		driver = new HtmlUnitDriver(BrowserVersion.CHROME);
   		driver.setJavascriptEnabled(true);
   	 }
	@Override
	public Object call() throws Exception {
	System.out.println("Calling  for "+section+" "+ baseUrl +"  " +otherUrls); 
		return null;
	} 
   	 
	}
}
