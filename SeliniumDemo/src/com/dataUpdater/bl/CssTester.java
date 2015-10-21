package com.dataUpdater.bl;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.bl.URLResolver;

public class CssTester {
   static String productUrl;
   
    public static void main(String[] args) {
        HtmlUnitDriver driver = new HtmlUnitDriver();
       /*driver.get("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html#subcategory=mobile");
       System.out.println("Name " +driver.findElement(By.cssSelector("#msp_body > div > div.msplistleft > div.productlistmiddle > div.listitems_rd.info-items-5 > div.product-list > div:nth-child(5) > div > a")).getText() );
      // System.out.println("Price " +driver.findElement(By.id("priceblock_ourprice")).getText() );
       */
	
        
        String url = "http://www.mysmartprice.com/out/sendtostore.php?mspid=189184&access_point=desktop&l1=c&top_category=electronics&category=computer&id=276288230&rk=12125&store=amazon";
        driver.get(url);
            
        
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                boolean flag = false;
                //System.out.println(d.getCurrentUrl());
                if(d.getCurrentUrl().startsWith("http://www."+"amazon")){
                    
                    flag = true;
                }else if(d.getCurrentUrl().startsWith("https://"+"amazon")){
                    flag = true;
                    
                }else
                {
                    flag = true;
                    
                }
            return flag;
            }
        });
        
        System.out.println(driver.getCurrentUrl());
        driver.close();
    }
    
}
