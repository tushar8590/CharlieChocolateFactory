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
	
        
        String url = "http://www.mysmartprice.com/product/mobile/maxx-arc-mx1i-mst4966-other#tab_spec";
        driver.get(url);
        
       //   List<WebElement> listTh = driver.findElementsByXPath("//table[contains(@class,'table table-bordered table-condensed')]//tr");

         /* for(int i = 0;i<listTh.size();i++){
              WebElement elem1 =  listTh.get(i);
              String data = elem1.getText();
              System.out.println(data); 
          }*/
           
        String  header = "";
        String  key = "";
        String  value = "";
        String combinedVal[] =null; 
        StringBuilder prdSpec = new StringBuilder();

        for(int i=1;i<=60;i++)
          {
              try{    
                      String divVal = driver.findElement(By.xpath("//*[@id='msp_body']/div/div[5]/div[2]/div[1]/div/table/tbody/tr["+i+"]")).getText();
                      System.out.println(divVal);
                      /*if(divVal.split("\n").length > 1){ // its key - value 
                          combinedVal = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText().split("\n");
                          key = combinedVal[0];
                          value = combinedVal[1];
                          prdSpec.append(key+"|");
                          prdSpec.append(value+";");
                     }else{
                         header = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText();
                         prdSpec.append("#"+header+";");
                     }*/
              }
             
              catch(Exception e){
                  e.printStackTrace();
                 
                   continue;}

          }
        System.out.println(prdSpec);
       
        driver.close();
    }
    
}
