package com.dataUpdater.bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataUpdater.model.Product;

public class TestDataUpdater implements VendorDataUpdater {

	
	private String url;
	private HtmlUnitDriver driver;
	private String mode;
    private Product product;
	private String vendor;
    private JDBCConnection conn; 
	
	public TestDataUpdater(String url,Product product, HtmlUnitDriver driver, String mode,
			String vendor) {
		this.url = url;
		this.driver = driver;
		this.mode = mode;
		this.product = product;
		this.vendor = vendor;
	}
	
	
	public TestDataUpdater(String url, HtmlUnitDriver driver, String mode,
			String vendor) {
		this.url = url;
		this.driver = driver;
		this.mode = mode;
		this.vendor = vendor;
	}

	public void processData() {
		if (vendor.equalsIgnoreCase("amazon.in")) {
			try {
				System.out.println("Getting the data");
				driver.get(url);
				String title = driver.findElement(
						By.cssSelector("#productTitle")).getText();
				product.setProductModel(title);
				System.out.println(title);

				String price = driver.findElement(
						By.cssSelector("#olp_feature_div > div > span > span"))
						.getText();
				product.setProductPrice(price);
				System.out.println(price);

				String starRating = driver
						.findElement(
								By.cssSelector("#acrPopover > span.a-declarative > a > i.a-icon.a-icon-star.a-star-4"))
						.getAttribute("class").toString();
				product.setProductRating(starRating);
				System.out.println(starRating);

				if (mode.equalsIgnoreCase("update")) {
					insertData();
				} else
					updateData();

			} catch (Exception e) {
				System.out.println("Data Not Available");
			}

		}
		
		if (vendor.equalsIgnoreCase("infibeam")) {
			try{
				System.out.println(this.getClass().getName());
				driver.get(url);
				
				String title = driver.findElement(By.cssSelector("#title > h1")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#price-after-discount > span.price")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				String starRating = driver.findElement(By.cssSelector("#review_and_rating_summary > span.rating-star > img:nth-child(1)")).getAttribute("alt").toString();
				product.setProductRating(starRating);
				System.out.println(starRating);
				
				if(mode.equalsIgnoreCase("update")){
					insertData();
				}else
					updateData();
				
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				}
		}
		
		if (vendor.equalsIgnoreCase("homeshop18")) {
			try{
				System.out.println("homeshop18");
				System.out.println(url);
				driver.get(url);
				
				String title = driver.findElement(By.xpath("//*[@id='productTitleInPDP']")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#hs18Price > span")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				String starRating = driver.findElement(By.cssSelector("#avgProductRatingPDPDiv > span")).getText();
				product.setProductRating(starRating);
				System.out.println(starRating);
				
				String stock = driver.findElement(By.cssSelector("#stock-status > strong")).getText();
				product.setProductStock(stock);
				System.out.println(stock);
				
				if(mode.equalsIgnoreCase("update")){
					insertData();
				}else
					updateData();
				
				
			}catch(Exception e){
				e.printStackTrace();
				//System.out.println("Data Not Available");
				}
		}
		
		if (vendor.equalsIgnoreCase("croma")) {
			try{
				System.out.println("Croma");
				driver.get(url);
				
				String title = driver.findElement(By.cssSelector("#form1 > section > div.pDesc > h1")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#form1 > section > div.pDesc > div.cta")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				String starRating = driver.findElement(By.cssSelector("#testfreaks-badge > div > div.tf-based")).getText();
				product.setProductRating(starRating);
				System.out.println(starRating);
				
							
				if(mode.equalsIgnoreCase("update")){
					insertData();
				}else
					updateData();
				
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				}
		}

	}

	@Override
	public boolean insertData() {

		boolean flag = false;
		
		if(this.product!=null){
			conn = JDBCConnection.getInstance(); 
			List<String> params = new ArrayList<String>(); 
			params.add(product.getProductId());
			params.add(product.getProductModel());
			params.add(product.getProductURL());
			params.add(product.getProductWebsite());
			params.add(product.getProductOffer());
			params.add(product.getProductPrice());
			params.add(product.getProductStock());
			params.add(product.getProductColor());
			params.add(product.getProductRating());
			String sql = SQLQueries.insertProductVendor;
			flag = conn.upsertData(sql, params);
			conn.closeConnection();
		}
		  
		  
		  
		 
		return flag;

	}

	@Override
	public boolean updateData() {
		// TODO Auto-generated method stub
		return false;
	}

}
