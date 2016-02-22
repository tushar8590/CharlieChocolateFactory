package com.dataUpdater.bl;

import java.util.List;

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
   
    public static void main(String[] args) {
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
       /*driver.get("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html#subcategory=mobile");
       System.out.println("Name " +driver.findElement(By.cssSelector("#msp_body > div > div.msplistleft > div.productlistmiddle > div.listitems_rd.info-items-5 > div.product-list > div:nth-child(5) > div > a")).getText() );
      // System.out.println("Price " +driver.findElement(By.id("priceblock_ourprice")).getText() );
       */
	
        
        String url = "http://www.mysmartprice.com/product/accessories/strontium-nitro-32gb-class-10-microsd-hc-xc-memory-card-mst59103-other#tab_spec";
        driver.get(url);
                                                        
        String  productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]")).getText();

        
        System.out.println(productUrl);
        
       
        driver.close();
    }
    
}
