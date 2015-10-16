package com.dataUpdater.bl;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        List<WebElement> elems = driver.findElements(By.xpath("//*[@id='msp_body']/div/div[4]/div/div[1]/div/table"));
       System.out.println(elems.size());
        for(int i =1;i<=44;i++){
        	System.out.println(driver.findElement(By.xpath("//*[@id='msp_body']/div/div[4]/div/div[1]/div/table/tbody/tr["+i+"]")).getText());
        }
        driver.close();
    }
    
}
