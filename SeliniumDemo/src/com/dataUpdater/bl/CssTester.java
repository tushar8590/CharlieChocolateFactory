package com.dataUpdater.bl;

import java.io.IOException;
import java.net.MalformedURLException;
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
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlMeta;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlStrong;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;

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
	
        
        //String url = "http://www.bagittoday.com/microsoft-office-365-home-premium-product-key-card/pr-526745_c-2451/";
        
       //String url = "http://www.amazon.in/Apple-iPhone-5s-Space-Grey/dp/B00FXLC9V4/ref=sr_1_1?s=electronics&ie=UTF8&qid=1464448904&sr=1-1&keywords=iphones";
        
       // String url = "http://www.ebay.in/itm/Apple-iPhone-6-s-64GB-Grey-Smartphone-with-1-year-warranty-/252388662816?hash=item3ac3895e20:g:diIAAOSw14xWONR2";

        
       // String url = " http://www.shopclues.com/apple-iphone-5s-16-gb-gold-20.html";
        
       // String url = "http://www.flipkart.com/samsung-galaxy-on7/p/itmedhx3jgmu2gps?pid=MOBECCA5SMRSKCNY&al=QSKw12LdpAf4WBTuwU5RRMldugMWZuE7Qdj0IGOOVqvHGf5fDem2EjgVjGaghR1aBU%2Fn0geTbXw%3D&ref=L%3A-3132028826391721437&srno=p_2&findingMethod=Search&otracker=start";
       
       // String url = "http://www.mysmartprice.com/mobile/medulla-u8-bluetooth-smartwatch-msp10146";

        //String url = "http://www.mysmartprice.com/mobile/apple-iphone-5s-msp3216";
        
		 //String url = "http://shopping.indiatimes.com/mobiles/lyf/lyf-water-5-black/44550/p_B7736854";

       // String url = "http://www.homeshop18.com/xolo-12-7-cm-5-inch-dragontrail-4g-3gb-ram-android-phablet/mobiles/mobile-phones/product:33497847/cid:3027/";
        
      // String  url = "http://www.snapdeal.com/product/samsung-tizen-z1/214936653";
       
       //String  url = " http://www.amazon.in/gp/product/B01H3PDWT0/ref=s9_simh_gw_g107_i4_r";
       
       String  url = "https://paytm.com/shop/p/apple-ipad-air-2-wifi-16-gb-golden-MOBAPPLE-IPAD-ASMAR4447160141E4C";
    
        
      //  driver.get(url);
        
        //List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'main_price')]");
       // List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'selling-price omniture-field')]");
        //List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@id,'priceblock_ourprice')]");
        //List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'notranslate')]");
       //List<WebElement> listTh = driver.findElementsByXPath("//meta[contains(@itemprop,'price')]");
        //List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'selling-price')]");

      //  List<WebElement> listTh = driver.findElementsByXPath("//div[contains(@class,'bttn js-prc-tbl__gts-btn')]");
       // WebElement elem = listTh.get(0);
        //String price = elem.getAttribute("data-url").toString(); 
        
       
        //WebElement elem = listTh.get(0);
        //String price = elem.getText().toString(); 
        
        //html/body/div[4]/div[4]/div[1]/div[1]/div[1]/div/div[1]/div[4]/div
        //FK <span class="selling-price omniture-field" data-omnifield="eVar48" data-evar48="5999">Rs. 5,999</span>
       // eta[itemprop=price]
      //*[@id="prcIsum"]
       //<span class="notranslate" id="prcIsum" itemprop="price" style="">Rs. 52,999.00</span>
        //div[itemprop=price]
        //span[id=priceblock_ourprice]
        //span[class=selling-price omniture-field]
       /* String content=driver.getPageSource();
      
        WebClient webClient;
    	webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);//if you don't need css
        webClient.getOptions().setJavaScriptEnabled(false);//if you don't need js
*/      //*[@id="container"]/div/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[2]/div[2]/div[1]/div/div[1]
      //  #container > div > div.content > div.nsGi9O > div > div > div:nth-child(1) > div > div._2Cl4hZ > div > div._1MVZfW > div._3ZYEWO > div._2MUtYG > div > div._1vC4OE._37U4_g
        
        try {
            
            WebClient webClient = new WebClient();
            webClient.getOptions().setCssEnabled(false);//if you don't need css
            webClient.getOptions().setJavaScriptEnabled(false);
            Page page = webClient.getPage(url);
            WebResponse response = page.getWebResponse();
            String content = response.getContentAsString();
            //System.out.println(content);
            String price = "";
            if(content.contains("Offer Price")){
                price = content.substring(content.indexOf("Offer Price") + 11 ,content.indexOf("Offer Price") +22);
            }
            System.out.println(price);
            
            
        	/*HtmlPage 	page = webClient.getPage(url);

        	
        	//List<HtmlSpan> listTh =(List<HtmlSpan>) page.getByXPath("//span[contains(@class,'offerprice flt')]").get(0);
        	List<HtmlSpan> spans =  (List<HtmlSpan>) page.getByXPath("//span[contains(@ng-if,'!product.product.isOnlyCarCategory')]");
        	HtmlDivision elemPrice = (HtmlDivision) page.getByXPath("//span[contains(@ng-if,'!product.product.isOnlyCarCategory')]").get(0);
        	String price = elemPrice.getTextContent().replaceAll("[^0-9]", "");  
        	System.out.println(price);

        	if( page.getByXPath("//meta[contains(@itemprop,'ratingValue')]").size() > 0)
        	{
        		HtmlMeta elemRating = (HtmlMeta) page.getByXPath("//meta[contains(@itemprop,'ratingValue')]").get(0);
        		String rating = elemRating.getAttribute("content");   
        		System.out.println(rating);
        	}

        	*/

        } catch (FailingHttpStatusCodeException e) {
			
			e.printStackTrace();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
        /*List<WebElement> listlogo= driver.findElementsByXPath("//img[contains(@class,'prc-grid__logo')]");
        List<WebElement> listDelivery = driver.findElementsByXPath("//div[contains(@class,'prc-grid-expnd__optn js-str-dlvry')]");
        List<WebElement> listEmi = driver.findElementsByXPath("//span[contains(@class,'prc-grid__bold-txt')]");
        List<WebElement> listPrice = driver.findElementsByXPath("//span[contains(@class,'prc-grid__prc-val')]");
        List<WebElement> listUrl = driver.findElementsByXPath("//div[contains(@class,'bttn js-prc-tbl__gts-btn bttn--gts prc-grid__gts-btn--mdl')]");*/

        
      /*  for(int i = 0; i < listlogo.size();i++){
        	WebElement elemPrice = listPrice.get(i);
        	String price = elemPrice.getText().toString();   
        	System.out.print(price+"    ");

        	WebElement elemDelivery = listDelivery.get(i);
        	String productDelivery = elemDelivery.getText().toString(); 
        	//   String price = elem.getText().toString();   
        	System.out.print(productDelivery+"  ");

        	WebElement elemEmi = listEmi.get(i);
        	String productEmi = elemEmi.getText().toString(); 
        	//   String price = elem.getText().toString();   
        	System.out.print(productEmi+"  ");

        	WebElement elemUrl = listUrl.get(i);
        	String productUrl = elemUrl.getAttribute("data-url").toString(); 
        	//   String price = elem.getText().toString();   

        	System.out.println(productUrl);


}*/
        driver.close();
    }
    
}
