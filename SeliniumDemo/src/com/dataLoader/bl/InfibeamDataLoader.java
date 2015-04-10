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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataLoader.model.ProductMaster;
import com.dataLoader.model.VendorProductData;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class InfibeamDataLoader implements DataLoader {

	
	private List<ProductMaster> productMasterList;
	//private List<VendorProductData> itemResults = new ArrayList<VendorProductData>();
	private Map<ProductMaster,VendorProductData> itemMap;
	private static String category = "Computers and  Peripherals";
	JDBCConnection conn;

	@Override
	public void processList() {
		List<ProductMaster> items = getItemsList();

		Iterator<ProductMaster> itr = items.iterator();
		itemMap = new  HashMap<ProductMaster,VendorProductData>();
		int i = 0;
		while (itr.hasNext()) {
			try {
			ProductMaster pm = itr.next();
				
				//WebDriver driver = new FirefoxDriver();
				System.out.println(pm.getProductTitle());
				HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
			    driver.get("http://www.infibeam.com");
			    driver.findElement(By.id("suggest")).sendKeys(
						pm.getProductTitle());
			    driver.findElement(By.id("search-icon")).click();
			     String url = driver.findElement(By.cssSelector("#resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-img.col-md-12.col-xs-4 > a")).getAttribute("href").toString();
				
				String title = driver.findElement(By.cssSelector("#resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-content.col-md-12.col-xs-8 > div.title > a ")).getText();
				
			    
				String price = driver.findElement(By.cssSelector("#resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-content.col-md-12.col-xs-8 > div.price.row > span.final-price")).getText();
			    
				VendorProductData vendorData = new VendorProductData(url.substring(url.lastIndexOf("variantId")+10, url.length()),
						title,
						price, 
						url, 
						"infibeam",
						"", 
						"");
				
				
				itemMap.put(pm, vendorData);
				driver.close();

				driver.quit();
				// calling the save method 
				if(saveResults(itemMap)){
					i++;
				System.out.println("Inserted for "+pm.getProductTitle());
				}
				itemMap.clear();
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
		return productMasterList;
	}

	@Override
	public boolean populateList() {
		conn =   JDBCConnection.getInstance();
		boolean result = false;
		productMasterList = new ArrayList<ProductMaster>();
		
		String mastDataLoadQuery = SQLQueries.infibeamRawProductMaseter;
		List<String> param = new ArrayList<String>();
				param.add(category);
				
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

	@Override
	public boolean saveResults(Map<ProductMaster, VendorProductData> results) {
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
			
			flag = conn.insertData(sql, params,true,pm.getProductId(),"infi");
		}
		conn.closeConnection();
		return flag;
	}

}
