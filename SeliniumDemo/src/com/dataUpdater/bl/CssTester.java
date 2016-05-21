package com.dataUpdater.bl;

import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.bl.URLResolver;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class CssTester {
   static String productUrl;
   static{
       java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
       System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
      }
    public static void main(String[] args) {
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
       /*driver.get("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html#subcategory=mobile");
       System.out.println("Name " +driver.findElement(By.cssSelector("#msp_body > div > div.msplistleft > div.productlistmiddle > div.listitems_rd.info-items-5 > div.product-list > div:nth-child(5) > div > a")).getText() );
      // System.out.println("Price " +driver.findElement(By.id("priceblock_ourprice")).getText() );
       */
	
        
        String url = "http://www.bagittoday.com/microsoft-office-365-home-premium-product-key-card/pr-526745_c-2451/";
        driver.get(url);
        
         List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'main_price')]");

         WebElement elem = listTh.get(0);
         String price = elem.getText().toString().replaceAll("[^0-9.]", "");   
     System.out.println(price);
       
        driver.close();
    }
    
}
