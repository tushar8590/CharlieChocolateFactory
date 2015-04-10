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

public class FlipkartMobileDataLoader implements DataLoader {

	private List<ProductMaster> productMasterList;
	//private List<VendorProductData> itemResults = new ArrayList<VendorProductData>();
	private Map<ProductMaster,VendorProductData> itemMap;
	private static String category = "Computers and  Peripherals";
	JDBCConnection conn;
	
	@Override
	public List<ProductMaster> getItemsList() {
		return productMasterList;
	}

	@Override
	public boolean populateList() {
		conn =   JDBCConnection.getInstance();
		boolean result = false;
		productMasterList = new ArrayList<ProductMaster>();
		
		String mastDataLoadQuery = SQLQueries.flipkartRawProductMaseter;
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
			
			flag = conn.insertData(sql, params,true,pm.getProductId(),"flipkart");
		}
		conn.closeConnection();
		return flag;
	}

	@Override
	public void processList() {
		List<ProductMaster> items = getItemsList();

		Iterator<ProductMaster> itr = items.iterator();
		itemMap = new  HashMap<ProductMaster,VendorProductData>();
		int i = 0;
		while (itr.hasNext()) {
			try {
			ProductMaster pm = itr.next();
				i++;
				//WebDriver driver = new FirefoxDriver();
				System.out.println(pm.getProductTitle());
				HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
			    driver.get("http://www.flipkart.com");
			    driver.findElement(By.xpath("//*[@id='fk-top-search-box']")).sendKeys(
						pm.getProductTitle());
			    driver.findElement(By.className("search-bar-submit")).click();
			    
		  
			  WebElement id = driver.findElement(ByXPath.xpath("//*[@id='products']/div[1]/div[1]/div"));  
			    
			    String asinid = id.getAttribute("data-pid").toString();
			    //String asinid = id.getText();
			    
			    WebElement title = driver
			    	      .findElement(By.xpath("//*[@id='products']/div[1]/div[1]/div/div[2]/div[1]/a"));
			    	    String result = title.getAttribute("title").toString();

			    	    WebElement price=null;
			    	     try{
			    	          price = driver
			    	           .findElement(By
			    	             .xpath("//*[@id='products']/div[1]/div[1]/div/div[2]/div[5]/div/div[1]/span"));
			    	     }catch(Exception e){
			    	       price = driver
			    	        .findElement(By
			    	          .xpath("//*[@id='products']/div[1]/div[1]/div/div[2]/div[6]/div/div[1]/span"));
			    	     }
			    	         
			    	     String url= title.getAttribute("href");
				
	
				VendorProductData vendorData = new VendorProductData(asinid,
						result,
						price.getText(), 
						url.toString(), 
						"flipkart",
						"", 
						"");
			
				
				itemMap.put(pm, vendorData);
				driver.close();

				driver.quit();
				// calling the save method 
				if(saveResults(itemMap))
				System.out.println("Inserted for "+pm.getProductTitle());
				itemMap.clear();
				if (i == 1) {
					//Thread.sleep(2000);
					break;
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

	

	
	
}
