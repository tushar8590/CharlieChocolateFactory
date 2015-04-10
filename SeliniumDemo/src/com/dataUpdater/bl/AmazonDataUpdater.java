package com.dataUpdater.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataLoader.model.ProductMaster;
import com.dataLoader.model.VendorProductData;

public class AmazonDataUpdater  implements VendorDataUpdater{

	
	private String url;
	private HtmlUnitDriver driver;
	private String mode;
	
	public AmazonDataUpdater(String url,HtmlUnitDriver driver,String mode){
		this.url = url;
		this.driver = driver;
		this.mode = mode;
	}
	
	@Override
	public void processData() {
		System.out.println(this.getClass().getName());
		// get the url, price, rating from the webpage
		try{
			System.out.println("Getting the data");
		driver.get(url);
		String title = driver.findElement(By.cssSelector("#productTitle")).getText();
		System.out.println(title);
		
		
		String price = driver.findElement(By.cssSelector("#olp_feature_div > div > span > span")).getText();
		System.out.println(price);
		
		String starRating = driver.findElement(By.cssSelector("#acrPopover > span.a-declarative > a > i.a-icon.a-icon-star.a-star-4")).getAttribute("class").toString();
		System.out.println(starRating);
		
		
		
		// create the object of Product to insert/update the data
		
		
		
		
		
		if(mode.equalsIgnoreCase("update")){
			insertData();
		}else
			updateData();
		
		
		}catch(Exception e){
			System.out.println("Data Not Available");
		}

	}

	@Override
	public boolean insertData() {
		
		boolean flag = false;
		/*conn = JDBCConnection.getInstance();
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
			
			flag = conn.insertData(sql, params,false,pm.getProductId(),"amazon");
		}
		conn.closeConnection();*/
		return flag;
	
	}

	@Override
	public boolean updateData() {
		// TODO Auto-generated method stub
		return false;
	}

}
