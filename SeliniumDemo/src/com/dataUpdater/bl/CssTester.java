package com.dataUpdater.bl;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CssTester {
   static String productUrl;
    public static void main(String[] args) {
        HtmlUnitDriver driver = new HtmlUnitDriver();
       /*driver.get("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html#subcategory=mobile");
       System.out.println("Name " +driver.findElement(By.cssSelector("#msp_body > div > div.msplistleft > div.productlistmiddle > div.listitems_rd.info-items-5 > div.product-list > div:nth-child(5) > div > a")).getText() );
      // System.out.println("Price " +driver.findElement(By.id("priceblock_ourprice")).getText() );
       */
        driver.get("http://www.mysmartprice.com/mobile/pricelist/pages/smart-watches-price-list-in-india-2.html");
        for(int i = 4 ;i<=51;i++){
            System.out.println("Count = " + i);
            productUrl = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[1]/div[4]/div[1]/div[3]/div["+i+"]/div/a")).getAttribute("href");            										   //*[@id="msp_body"]/div/div[1]/div[5]/div[1]/div[4]/div[5]/div/a
                                                       //*[@id="msp_body"]/div/div[1]/div[5]/div[1]/div[4]/div[6]/div/a
                                                       
            System.out.println(productUrl);
          //
            //this.saveData(productUrl, section);
            
        }
        driver.close();
    }
    
}
