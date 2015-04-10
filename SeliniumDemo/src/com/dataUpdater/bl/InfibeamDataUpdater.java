package com.dataUpdater.bl;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class InfibeamDataUpdater  implements VendorDataUpdater{

	
	private String url;
	private HtmlUnitDriver driver;
	private String mode;
	public InfibeamDataUpdater(String url,HtmlUnitDriver driver,String mode){
		
			this.url = url;
			this.driver = driver;
			this.mode = mode;
	}

	@Override
	public void processData() {
		try{
		System.out.println(this.getClass().getName());
		// get the url, price, rating from the webpage
		
		driver.get(url);
		
		String title = driver.findElement(By.cssSelector("#title > h1")).getText();
		System.out.println(title);
		
		
		String price = driver.findElement(By.cssSelector("#price-after-discount > span.price")).getText();
		System.out.println(price);
		
		String starRating = driver.findElement(By.cssSelector("#review_and_rating_summary > span.rating-star > img:nth-child(1)")).getAttribute("alt").toString();
		System.out.println(starRating);
		
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateData() {
		// TODO Auto-generated method stub
		return false;
	}

}
