package com.dataUpdater.bl;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class CssTester {
    
    public static void main(String[] args) {
        HtmlUnitDriver driver = new HtmlUnitDriver();
       driver.get("http://www.google.co.in");
       System.out.println("Name " +driver.findElement(By.xpath("//*[@id='productTitle']")).getText() );
       System.out.println("Price " +driver.findElement(By.id("priceblock_ourprice")).getText() );
       
        
    }
    
}
