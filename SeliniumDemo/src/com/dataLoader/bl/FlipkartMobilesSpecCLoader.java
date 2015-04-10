package com.dataLoader.bl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataLoader.model.ProductMaster;
import com.dataLoader.model.VendorProductData;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class FlipkartMobilesSpecCLoader implements DataLoader {
	
	//private List<VendorProductData> productMasterList;
	//private List<VendorProductData> itemResults = new ArrayList<VendorProductData>();
	Map<String,List<String>> featureMap;
	//private Map<ProductMaster,VendorProductData> itemMap;
	private static String vendor = "flipkart";
	private static String category = "Digital Cameras";
	private List<VendorProductData> vendorProductDataList;
	JDBCConnection conn;
	
	
	public List<VendorProductData> getItemsListVendor() {
		return vendorProductDataList;
	}

	@Override
	public boolean populateList() {
		conn =   JDBCConnection.getInstance();
		boolean result = false;
		vendorProductDataList = new ArrayList<VendorProductData>();
		
		//String urlDataLoadQuery = SQLQueries.flipkartProductURL;
		//String urlDataLoadQuery = SQLQueries.flipkartCamsURL;
		String urlDataLoadQuery = SQLQueries.getURLFromPCIFeed;
		List<String> param = new ArrayList<String>();
				
				//param.add(category);
				param.add(vendor);
		ResultSet rs = conn.executeQuery(urlDataLoadQuery,param);
		if(rs!=null){
			try {
				while(rs.next()){
					VendorProductData pm = new VendorProductData(rs.getString("product_id"),
						rs.getString("url"));
					    vendorProductDataList.add(pm);
					   // System.out.println(pm);	
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

	
	
	public boolean saveSpecResult(String id,StringBuffer specs) {
		boolean flag = false;boolean flag2 = false;
		conn = JDBCConnection.getInstance();
		List<String> params = new ArrayList<String>();
		// String sql = SQLQueries.insertProductSpecs;
		String sql = SQLQueries.insertPciSpecMaster;
		 params.add(id);
		 params.add(specs.toString());
	     flag = conn.upsertData(sql,params);
	     params.remove(1);
		 flag2 = conn.upsertData(SQLQueries.updatePCISpecFlag, params);
		conn.closeConnection();
		return flag;
	}

	@Override
	@SuppressWarnings({"unchecked","CSS error","CSS warning"})
	public void processList() {
		List<VendorProductData> items = getItemsListVendor();

		Iterator<VendorProductData> itr = items.iterator();
		//itemMap = new  HashMap<ProductMaster,VendorProductData>();
		featureMap = new HashMap<String,List<String>>();
		int i = 0;
		List<String> generalFeatures = new ArrayList<String>();
		while (itr.hasNext()) {
			try {
				VendorProductData pv = itr.next();
				i++;
				//WebDriver driver = new FirefoxDriver();
				System.out.println(pv.getProductId());
				HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
				System.out.println(pv.getProductURL());
			    driver.get(pv.getProductURL());
			    
			    
			    // start getting the HTML elements from the page
			   			  //  String topLevelParentCSSPath = "#fk-mainbody-id > div > div:nth-child(13) > div.gd-col.gu12 > div.productSpecs.specSection";
			   /* List<WebElement> topLevelElements = driver.findElements(By.cssSelector(topLevelParentCSSPath));
			    System.out.println(topLevelElements.toString());
			    break;*/
			    
			  //String topLevelParentCSSPath = "#fk-mainbody-id > div > div:nth-child(17) > div.gd-col.gu12 > div.productSpecs.specSection";
			  String topLevelParentCSSPath = "#fk-mainbody-id > div > div:nth-child(13) > div.gd-col.gu12 > div.productSpecs.specSection";
			  
			    List<WebElement> topLevelElements = driver.findElements(By.cssSelector(topLevelParentCSSPath));
			    
			    System.out.println(topLevelElements.size());
			    
			    
			    StringBuffer features = new StringBuffer();
			    for(int x =2;x<=15;x++){
			    
			    	String parentPath =  topLevelParentCSSPath+" > table:nth-child("+x+") > tbody > tr";
				    List<WebElement> rows = driver.findElements(By.cssSelector(parentPath));
				    // for (WebElement row : rows) {
				  // System.out.println(rows);
				   
				    for(int m = 1;m<=rows.size();m++){
				    	
				    	if(m==1){
				    		//k++;
				    		 features.append("#"+driver.findElement(By.cssSelector(parentPath+":nth-child("+m+")> th")).getText());
				    		 
				    		continue;
				    	}
				    	String childPath = parentPath + ":nth-child("+m+") > td.specsKey";
				    	//System.out.println(childPath);
				    	try{
				    	WebElement key = driver.findElement(By.cssSelector(childPath));
				    	
				        //System.out.println(key.getText());
				        WebElement val = driver.findElement(By.cssSelector(parentPath+":nth-child("+m+")> td.specsValue"));
				       // System.out.println(key.getText()+"|"+val.getText()+";");
				        features.append((";"+key.getText()+"|"+val.getText()));
				    	}catch(Exception e){
				    		continue;
				    	}
				        
				    }
				    //featureMap.put(feature, childFeatureList);
			    }
			    
			   System.out.println(features); 
			    
			   
			 
				driver.close();

				driver.quit();  
				// calling the save method 
				if(saveSpecResult(pv.getProductId(),features))
				System.out.println("Inserted for "+pv.getProductId());
				featureMap.clear();
				if (i == 1) {
					//Thread.sleep(2000);
					//break;
				}
			}
		 catch (Exception e) {
			 e.printStackTrace();
			 itr.remove();
			 continue;
		 }
		}
		//saveResults(itemMap);
System.out.println("Data Inserted for  "+i+" products");
	}

	@Override
	public List<ProductMaster> getItemsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveResults(Map<ProductMaster, VendorProductData> results) {
		// TODO Auto-generated method stub
		return false;
	}

}
