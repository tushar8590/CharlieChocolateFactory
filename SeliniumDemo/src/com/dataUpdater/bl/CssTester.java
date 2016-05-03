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
	
        
        String url = "http://www.homeshop18.com/zebronics-cm-hd-web-camera-crisp/computers-tablets/computer-peripherals/product:30606449/cid:3285/";
        driver.get(url);
        
         List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@id,'hs18Price')]");

        for(WebElement elem : listTh){
            System.out.println(elem.getText());
        }
     
       
        driver.close();
    }
    
}
