package com.dataLoader.bl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.*;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataLoader.model.ProductMaster;
import com.dataLoader.model.VendorProductData;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleDataLoader  {
	

	private List<ProductMaster> productMasterList;
	//private List<VendorProductData> itemResults = new ArrayList<VendorProductData>();
	private Map<ProductMaster,VendorProductData> itemMap;
	//Statement stmt;
	JDBCConnection 	conn = JDBCConnection.getInstance();
	
	public GoogleDataLoader() {
		
	}

	
	public static void main(String ar[]){
	    
	}
	
	public void processList(String cat) {
		
		List<ProductMaster> items = getItemsList();

		Iterator<ProductMaster> itr = items.iterator();
		itemMap = new  HashMap<ProductMaster,VendorProductData>();
		int i = 0;
		conn = JDBCConnection.getInstance();
		while (itr.hasNext()) {
		try {
			
			boolean flag = false;

			ProductMaster pm = itr.next();
				i++;
				//WebDriver driver = new FirefoxDriver();
				  
				  HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
				  driver.setJavascriptEnabled(true);
				 
				
				String search = pm.getProductTitle() + " + top online vendors";
				System.out.println("browser "+ search);
				driver.get("http://www.google.co.in");
				driver.findElement(By.name("q")).sendKeys(search);
				driver.findElement(By.cssSelector("#tsf > div.tsf-p > div.jsb > center > input[type='submit']:nth-child(1)")).click();
				
				long end = System.currentTimeMillis() + 5000;
				 WebElement myDynamicElement = (new WebDriverWait(driver, 15))
			              .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
				 
				 List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));

				    // this are all the links you like to visit
				    for (WebElement webElement : findElements)
				    {
				    	String strURL=webElement.getAttribute("href");
				        System.out.println(""+webElement.getAttribute("href"));
				        String sql = "insert into shoes_url_temp (product_id,product_title,product_url) values(?,?,?)";
				      
				        List<String> params = new ArrayList<String>();
				        params.add(pm.getProductId());
				       params.add(pm.getProductTitle());
				        params.add(strURL);
				        
				       flag = conn.insertData(sql, params,true,pm.getProductId(),"google");
				       System.out.println(flag);
				        
					}
				   // if(flag)
				    driver.close();
				    driver.quit();
				//if (i == 8) {
				//	Thread.sleep(5000);
					
				//}
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

	
	public List<ProductMaster> getItemsList() {
		return productMasterList;
	}

	
	public boolean populateList(String cat) {
		conn =   JDBCConnection.getInstance();
		boolean result = false;
		productMasterList = new ArrayList<ProductMaster>();
		
		String mastDataLoadQuery = SQLQueries.googleShoesData;
		List<String> param = new ArrayList<String>();
				param.add("Mens Footwear");
				
		ResultSet rs = conn.executeQuery(mastDataLoadQuery,param);
		if(rs!=null){
			try {
				while(rs.next()){
						ProductMaster pm = new ProductMaster(rs.getString("product_id"),
						rs.getString("product_title"));
						productMasterList.add(pm);
						
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
	
	
	
	public boolean saveResults(Map<ProductMaster,VendorProductData> results) {
		boolean flag = false;
		conn = JDBCConnection.getInstance();
		List<String> params = new ArrayList<String>();
		for(Entry<ProductMaster, VendorProductData> entry : results.entrySet()){
			//System.out.println(entry.getKey().getProductTitle() + "   "+ entry.getValue().getProductTitle());
			ProductMaster pm = entry.getKey();
			VendorProductData vp  = entry.getValue();
			params.add(vp.getProductId());
			params.add(pm.getProductId());
			params.add(vp.getProductTitle());
			params.add(vp.getProductPrice());
			params.add(vp.getProductURL());
			params.add(vp.getProductVendor());
			params.add(vp.getProductReviewId());
			params.add(vp.getProductCouponId());
			params.add("");
			String sql = SQLQueries.insertProductVendor;
			
		}
		conn.closeConnection();
		return flag;
	}

}
