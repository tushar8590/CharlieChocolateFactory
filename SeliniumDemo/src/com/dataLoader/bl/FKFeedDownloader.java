package com.dataLoader.bl;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class FKFeedDownloader {
	
	
	public static void main (String args[]){
		
		
		//WebDriver driver = new FirefoxDriver();;
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
		driver.get("https://www.flipkart.com/affiliate/login");

		
		 driver.findElement(By.cssSelector("#login_email_id")).sendKeys("a123pp9@aapcompare.com");
		 driver.findElement(By.cssSelector("#login_password")).sendKeys("faap$1234");
	
		 driver.findElement(By.cssSelector("#submit")).click();
		 
		
		 // System.out.println(driver.findElement(By.cssSelector("#aff-body-block")));
		 
			String topLevelParentCSSPath = "#your-downloads-table";
			
			
			
			//System.out.println(driver.findElement(By.cssSelector("#affiliate-body-id > div > div > h2 > span.fllt.fk-uppercase.fk-font-16.lmargin10")).getText());
			
			
			/*WebElement myDynamicElement = (new WebDriverWait(driver, 10))
		              .until(ExpectedConditions.presenceOfElementLocated(By.className("your-downloads-pane")));
			*/
          
            List<WebElement> list = driver.findElements(By.xpath("//*"));
            for(WebElement elem:list){
            	System.out.println(elem);
            }
            
            
            for(int x =3;x<=15;x++){
            
               String parentPath =  topLevelParentCSSPath+" > tbody > tr:nth-child("+x+") > td.details";
              // String parentPath =  topLevelParentCSSPath;

                 String feedName = driver.findElement(By.cssSelector(parentPath)).getText();
                 
                
                 
                 
              String Url = driver.findElement(By.cssSelector(topLevelParentCSSPath+"tbody > tr:nth-child("+x+") > td.link > a")).getText();
                             
                   
                     System.out.println(feedName +"  ---  "+ Url);
                   
                      
                  }
                  //featureMap.put(feature, childFeatureList);
            }

		
}
