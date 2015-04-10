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
public class TestEbb  {




	public static void main(String args[]){


		try{

			
			HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
			//WebDriver driver = new FirefoxDriver();
		    driver.get("http://api.pricecheckindia.com");
		    
		 driver.findElement(By.cssSelector("body > div.container > div.row > div:nth-child(2) > form > fieldset > input.input-large")).sendKeys("tushar8590@gmail.com");;
		 driver.findElement(By.cssSelector("body > div.container > div.row > div:nth-child(2) > form > fieldset > input.span2")).sendKeys("12345678");;
		driver.findElement(By.cssSelector("body > div.container > div.row > div:nth-child(2) > form > fieldset > button")).click(); 
		driver.findElement(By.cssSelector("#catalogs-link > a")).click();   
		    
		    
		  
		 
		    
		    for(int x =9;x<=64;x++){
		    String path = "#stores > form > fieldset > table > tbody > tr:nth-child("+x+") > td:nth-child(4) > a";
		    	WebElement url = driver.findElement(By.cssSelector(path));
		    	System.out.println(url.getAttribute("href").toString());
			   
		    }
		    
		 
		 
			driver.close();

			driver.quit();  
	

		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}


}
