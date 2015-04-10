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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataLoader.model.ProductMaster;
import com.dataLoader.model.VendorProductData;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class TestEbayData  {




	public static void main(String args[]){


		try{

			WebDriver driver = new FirefoxDriver();
			/*  Commented for Amazon
				  HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);

				System.out.println("browser  "+ pm.getProductTitle());
				driver.get("http://www.amazon.in");

				Select select=new Select(driver.findElement(By.xpath("//*[@id='searchDropdownBox']")));

				select.selectByVisibleText("Electronics");
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

				//*[@id="result_0"]/div/div[3]/div/a/span/text()

				WebElement url = driver
						.findElement(By
								.xpath("//*[@id='result_0']/div/div[1]/div/div/a"));*/

			
			 //HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);

				
				driver.get("http://www.naaptol.com/");
				driver.findElement(By.xpath("//*[@id='header_search_text']")).sendKeys("Sony Xperia Z1");
				driver.findElement(By.xpath("//*[@id='header_search_category']/option[4]")).click();
				driver.findElement(By.cssSelector("#header_search > div.search > a")).click();
			
			//input button id = //*[@id="suggest"]    
				//	button= //*[@id="search-icon"]
				
				
			//	#resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-img.col-md-12.col-xs-4 > a > picture > img
																	//#resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-img.col-md-12.col-xs-4 > a > picture > img
				//List<WebElement> ids = driver.findElements(By.xpath("//*[@id='productItem1']"));//("//*[@id='resultPane']/div[1]/div[2]/div/div[1]/a/picture"));//.getAttribute("data-title").toString();
			
				//System.out.println(ids.size());
				String href = driver.findElement(By.cssSelector("#productItem1 > div.item_image > a")).getAttribute("href").toString();
				//String href = id.getAttribute("href");
				//String title = id.getAttribute("title");
				
				
					//title = #resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-img.col-md-12.col-xs-4 > a > picture > img (title) 
				//String price = driver.findElement(By.cssSelector("#resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-content.col-md-12.col-xs-8 > div.price.row > span.final-price")).getText();
					//price #resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-content.col-md-12.col-xs-8 > div.price.row > span.final-price > span
				//#resultPane > div.resultsrow.row > div:nth-child(2) > div > div.product-content.col-md-12.col-xs-8 > div.price.row > span.scratch
					
				System.out.println(href.substring(href.lastIndexOf("variantId")+10, href.length()));
				
				System.out.println(href);
				//System.out.println(title);
				//System.out.println(price);
				
		


			//driver.get("http://www.ebay.in");


			//driver.findElement(By.id("gh-ac")).sendKeys(
			//		"apple iphone 5s");

			//Select select=new Select(driver.findElement(By.xpath("//*[@id='gh-cat']")));

			
			
			//select.selectByValue(select.getOptions().get(23).getText());

			//driver.findElement(By.className("btn")).click();


			//WebElement id = driver
		//	//		.findElement(By
			//				.cssSelector("sresult"));
//
		

			//					

		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}


}
