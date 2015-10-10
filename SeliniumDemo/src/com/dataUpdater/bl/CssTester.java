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
		 String deliveryTime;
		 String emi;
		 String cod;
		 String rating;
        
        driver.get("http://www.mysmartprice.com/mobile/apple-iphone-6-msp4340");
        // image 
        System.out.println(driver.findElement(By.xpath("//*[@id='mspSingleImg']")).getAttribute("src"));
        // delivery
        System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div[3]/div[2]/div[4]/div[1]")).getText());
        //price
        System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div[3]/div[2]/div[5]/div[1]/div[1]")).getText());
        // emi
        System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div[3]/div[2]/div[3]/div[1]")).getText());
        //rating
        System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div[3]/div[2]/div[2]/div[2]")).getAttribute("data-callout"));
        //cod
        System.out.println(driver.findElement(By.xpath("//*[@id='pricetable']/div[4]/div[2]/div[3]/div[3]")).getAttribute("class"));
            //this.saveData(productUrl, section);
        driver.close();
    }
    
}
