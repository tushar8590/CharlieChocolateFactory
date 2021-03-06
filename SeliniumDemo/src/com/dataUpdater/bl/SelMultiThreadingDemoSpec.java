package com.dataUpdater.bl;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataUpdater.bl.MSPCatDataExtractor.DataExtractor;
import com.gargoylesoftware.htmlunit.BrowserVersion;



/**
 * 
 * Getting the data from msp_product_url whose temp_flag = 'F'
 * after inserting the SPec data it makes the  temp_flag = 'X' - for data found and Y for data not found
 * @author jn1831
 *
 */
public class SelMultiThreadingDemoSpec {

	static{
		  java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		  System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		 }
	Map<String, List<String>> urlMap;
	static String idBase = "ACNEW";
	int count;
	private static Map<String, Map<String, List<String>>> mainMap;
	private Map<String, List<String>> subMap;
	private List<String> urlList;
	JDBCConnection conn;

	public void getUrls() {
	    System.out.println("Starting at "+new Timestamp(new Date().getTime()));
		conn = JDBCConnection.getInstance();
		// String query = SQLQueries.getMspUrls;
		String mainQuery = "select distinct menu_level1 from msp_product_url where temp_flag = 'F'";
		// String query =
		// "SELECT DISTINCT section FROM msp_product_url WHERE  menu_level1 = 'mobiles' ORDER BY section;";
		ResultSet rs1 = conn.executeQuery(mainQuery, null);

		mainMap = new HashMap<>();

		try {
			while (rs1.next()) {
				String subMenuQuery = "SELECT DISTINCT section FROM msp_product_url WHERE  menu_level1 = '"
						+ rs1.getString("menu_level1") + "' and temp_flag = 'F' ORDER BY section";
				// get the prodct corr to each section
				ResultSet rs = conn.executeQuery(subMenuQuery, null);
				urlMap = new HashMap<>();
				while (rs.next()) {
					String getProductUrl = "select * from msp_product_url where section = '"
							+ rs.getString("section") + "' and temp_flag = 'F' LIMIT 300";
					ResultSet rsProductUrl = conn.executeQuery(getProductUrl,
							null);
					urlList = new ArrayList<>();
					while (rsProductUrl.next()) {
						urlList.add(rsProductUrl.getString("spec_url"));

					}
					urlMap.put(rs.getString("section"), urlList);
				}
				mainMap.put(rs1.getString("menu_level1"), urlMap);
			}
			System.out.println(urlList.size());
			System.out.println(urlMap.size());
			System.out.println(mainMap.size());

			/*
			 * urlMap.forEach((k,v) -> { idBase = "AC" + count; DataExtractor de
			 * = this.new DataExtractor(k,v,driver,idBase); count++; try {
			 * de.call(); } catch (Exception e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); } });
			 */
			System.out.println("Ending  at "+new Timestamp(new Date().getTime()));
		} catch (Exception e) {
		    System.out.println("Error at "+new Timestamp(new Date().getTime()));
			e.getMessage();
		} finally {

			// conn.closeConnection();
		}
	}
	
	
	public static void main(String[] args) {
		SelMultiThreadingDemoSpec mspSpecExtractor = new SelMultiThreadingDemoSpec();
		mspSpecExtractor.getUrls();
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
		ExecutorService executor = Executors.newFixedThreadPool(5);
		List<Future<String>> list = new ArrayList<Future<String>>();

		for(Map.Entry<String,Map<String,List<String>>> topLevelMenuMapEntry:mainMap.entrySet()){

		    for(Map.Entry<String, List<String>> sectionLevelEntry:topLevelMenuMapEntry.getValue().entrySet() ){

				Callable<String> callable = mspSpecExtractor.new DataExtractor(sectionLevelEntry.getValue(),sectionLevelEntry.getKey(), driver, idBase);
				
				Future<String> future = executor.submit(callable);
				list.add(future);
		    }
			
		
		}
		executor.shutdown();
		System.out.println("Shutting down ececutor");
	}
	
	class DataExtractor implements Callable{
		String query;
		List<String> params;
		JDBCConnection conn;
		List<String> url;
		String section;
		HtmlUnitDriver driver;
		String vendorUrl;
		String deliveryTime;
		String emi;
		String cod;
		String rating;
		String image;
		String price;
		String productid;


		public DataExtractor(List<String> baseUrl, String section,HtmlUnitDriver driver, String id) {
			this.url = baseUrl;
			this.section = section;
			this.driver = driver;
			this.productid = id;
			params = new ArrayList<>();
			conn = JDBCConnection.getInstance();

		}
		@Override
		public Object call() throws Exception {
			Iterator<String> itr = url.iterator();
			String currentUrl = "";
	    	 StringBuilder prdSpec = new StringBuilder();
	    //	 System.out.println("Calling");
			while (itr.hasNext()) {
				currentUrl = itr.next();
                System.out.println(currentUrl);

				try {
					driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
					driver.get(currentUrl);
					
					String  header = "";
		    		 String  key = "";
		    		 String  value = "";

		    		 

		    		     String combinedVal[] =null; 
		    		 for(int i = 1; i <70; i++)
		    		 {
		    			 /*try{
		    			 if(driver.findElements(By.xpath("//*[@id='msp_body']/div/div[5]/div/div[1]/div/table/tbody/tr["+i+"]/th")).size() > 0){
		    				 header = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[5]/div/div[1]/div/table/tbody/tr["+i+"]/th")).getText().toString();
		    				//                                      
		    				 prdSpec.append("#"+header+";");

		    			 }else{
		    				 key=  driver.findElement(By.xpath("//*[@id='msp_body']/div/div[5]/div/div[1]/div/table/tbody/tr["+i+"]/td[1]")).getText() ;
		    				 value = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[5]/div/div[1]/div/table/tbody/tr["+i+"]/td[2]")).getText() ;
		    				                                   
		    				 prdSpec.append(key+"|");
		    				 prdSpec.append(value+";");
		    			 }}catch(Exception e){continue;}*/
		    		     

                         try{
                             
                             String divVal = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText();
                             if(divVal.split("\n").length > 1){ // its key - value 
                                  combinedVal = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText().split("\n");
                                  key = combinedVal[0];
                                  value = combinedVal[1];
                                  prdSpec.append(key+"|");
                                  prdSpec.append(value+";");
                             }else{
                                 header = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText();
                                 prdSpec.append("#"+header+";");
                             }

		    		 }

		    		  catch (Exception e) {
		    		      continue;
		    		  }
		                    }
           this.saveData(prdSpec.toString(), currentUrl);
 	
		    		  
		    		 
				} catch (Exception e) {
					e.getMessage();
					saveSkipForNoData(currentUrl);
					continue;
					
					
				}finally{
					driver.quit();
					prdSpec.delete(0,prdSpec.length());
				}
				
			}

			return null;


		}
		private void saveData(String prdSpec,String currentUrl) {
			query = SQLQueries.updateMSPSpec;
			if(prdSpec == null)
			    prdSpec = " ";
			params.add(prdSpec);
			params.add(currentUrl);
			 conn.upsertData(query, params);
			params.clear();
			// System.out.println(url);
		}
		
		private void saveSkipForNoData(String currentUrl){
			System.out.println("No Data");
			query = SQLQueries.updateSkipForNoSpecData; 
			params.clear();
			params.add(currentUrl);
			 conn.upsertData(query, params);
				params.clear();
		}
		
	
	}
}
