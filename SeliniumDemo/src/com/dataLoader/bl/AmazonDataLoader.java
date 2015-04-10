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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataLoader.model.ProductMaster;
import com.dataLoader.model.VendorProductData;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class AmazonDataLoader implements DataLoader {
	

	private List<ProductMaster> productMasterList;
	//private List<VendorProductData> itemResults = new ArrayList<VendorProductData>();
	private Map<ProductMaster,VendorProductData> itemMap;
	
	JDBCConnection conn;
	
	
	public AmazonDataLoader() {
		
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
				HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
				driver.get("http://www.amazon.in");
				driver.findElement(By.id("twotabsearchtextbox")).sendKeys(
						pm.getProductTitle());
				driver.findElement(By.className("nav-submit-input")).click();
				
				WebElement id = driver
						.findElement(By
								.xpath("//*[@id='result_0']"));
				String asinid = id.getAttribute("data-asin").toString();
				
				WebElement title = driver
						.findElement(By
								.xpath("//*[@id='result_0']/div/div[2]/div[1]/a/h2"));
				String result = title.getText();

				WebElement price = driver
						.findElement(By
								.xpath("//*[@id='result_0']/div/div[3]/div/a/span"));

				WebElement url = driver
						.findElement(By
								.xpath("//*[@id='result_0']/div/div[1]/div/div/a"));
				////*[@id="result_0"]/div/div/div/div[2]/div[2]/div[1]/div[1]/a/span
				
				String searchResult = asinid +" " +result + " " + price.getText() + " "
						+ url.getAttribute("href").toString();
				//System.out.println(searchResult);
				
				VendorProductData vendorData = new VendorProductData(asinid,
						result,
						price.getText(), 
						url.getAttribute("href").toString(), 
						"amazon",
						"", 
						"");
				//itemResults.add(vendorData);
				
				itemMap.put(pm, vendorData);
				driver.close();

				driver.quit();
				// calling the save method 
				if(saveResults(itemMap))
				System.out.println("Inserted for "+pm.getProductTitle());
				itemMap.clear();
				if (i == 50) {
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

	@Override
	public List<ProductMaster> getItemsList() {
		return productMasterList;
	}

	@Override
	public boolean populateList() {
		conn =   JDBCConnection.getInstance();
		boolean result = false;
		productMasterList = new ArrayList<ProductMaster>();
		
		String mastDataLoadQuery = SQLQueries.rawProductMaster;
		List<String> param = new ArrayList<String>();
				param.add("Mobile Phones");
				
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
			
			flag = conn.insertData(sql, params,true,pm.getProductId(),"amazon");
		}
		conn.closeConnection();
		return flag;
	}

}
