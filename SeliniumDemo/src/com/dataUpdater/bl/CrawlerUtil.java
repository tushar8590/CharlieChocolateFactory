package com.dataUpdater.bl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.bl.URLResolver;
import com.dataLoader.dao.JDBCConnection;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlMeta;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlStrong;

public class CrawlerUtil {
    private static WebClient webClient;
    static{
    
    webClient = new WebClient(BrowserVersion.CHROME);
    webClient.getOptions().setCssEnabled(false);//if you don't need css
    webClient.getOptions().setJavaScriptEnabled(false);
    }
    
    public static void callProcessMethod(String website, String URL, JDBCConnection con) throws SQLException, IOException{

        String URl = URL;
        
        String storeName = website;
        String price = "";
      //  System.out.println(website);
        System.out.println(URl);
        if(storeName.equalsIgnoreCase("snapdeal")){
            price = processSD(URl);
        }
        else if(storeName.equalsIgnoreCase("flipkart"))
        {
            price = processFK(URl);
           
        }//not working
        else if(storeName.equalsIgnoreCase("amazon")){
            price = processAmazon(URl);
        }
        else if(storeName.equalsIgnoreCase("shopclues")){
            price = processShopClues(URl);
        }
        else if(storeName.equalsIgnoreCase("infibeam")){
            price = processInfiBeam(URl);
        }//not working
        else if(storeName.equalsIgnoreCase("paytm")) {
            price = processPaytm(URl);
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(price);
            while (m.find()) {
                price = m.group();
            }
        }
        
        else if(storeName.equalsIgnoreCase("homeshop18")){
            price = processHomeshop18(URl);
        }
        else if(storeName.equalsIgnoreCase("ebay")){
            price = processEbay(URl);
        }

        else if(storeName.equalsIgnoreCase("naaptol")){
            price = processNaaptol(URl);
        }
       else if(storeName.equalsIgnoreCase("gadgets360")){
            price = processgadgets360(URl);
        }
              
        else{
            
        }
        if(!storeName.equalsIgnoreCase("paytm"))
            price = price.replaceAll("[^0-9].", "");
        
          float priceFloat = Float.parseFloat(price);
        insertUpdatedPrice( priceFloat,  website, URL, con );
    }

    public static String processSD(String URL) throws SQLException, IOException{
    	webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(false);
	    HtmlPage page = webClient.getPage(URL);
			HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@itemprop,'price')]").get(0);
			String price = elemPrice.getTextContent().replaceAll("\\D+", "");
			 System.out.println(price);
        return price;
    }

    public static String processFK(String prodctId){
       
        try {
        	return new HttpURLConnectionExample().getFKPrice(prodctId);
        }catch (Exception e) {
            System.out.println("Flipkart "+e.getMessage());
            return "0";
        }
    }

    
            // need to correct this method for id--- wild card needs to be implemented
    public static String processAmazon(String URL) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        HtmlPage page= webClient.getPage(URL);
        
        HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@id,'priceblock_ourprice')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9.]", "");  
    	//System.out.println(price);
    	
    	return price;
        
       
    }
    
    public static String processShopClues(String URL) throws SQLException, IOException{
         HtmlPage    page = webClient.getPage(URL);
    	HtmlDivision elemPrice = (HtmlDivision) page.getByXPath("//div[contains(@class,'price')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9]", "");  
    	//System.out.println(price);

    	return price;
        }

    public static String processInfiBeam(String URL) throws SQLException, IOException{
        
        HtmlPage    page = webClient.getPage(URL);
        HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'price')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9.]", "");  
    	//System.out.println(price);
    	return price;
  }
    
    //not working
    public static String processPaytm(String URL) throws SQLException, IOException{

        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);//if you don't need css
        webClient.getOptions().setJavaScriptEnabled(false);
        Page page = webClient.getPage(URL);
        WebResponse response = page.getWebResponse();
        String content = response.getContentAsString();
        //System.out.println(content);
        String price = "";
        if(content.contains("Offer Price")){
            price = content.substring(content.indexOf("Offer Price") + 11 ,content.indexOf("Offer Price") +23);
        }
       // System.out.println(price);
        return price;
      
        }
    
  
    //not working
    public static String processHomeshop18(String URL) throws SQLException, IOException{
        
       
       // driver.close();
        HtmlPage    page = webClient.getPage(URL);
            HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@id,'hs18Price')]").get(0);
        	String price = elemPrice.getTextContent().replaceAll("\\D+", "");   
        	//System.out.println(price);

        	return price;
        }
    
    
    public static String processEbay(String URL) throws SQLException, IOException{
        HtmlPage    page = webClient.getPage(URL);
        HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'notranslate')]").get(0);
    	String price = elemPrice.getAttribute("content");  
    	//System.out.println(price);
    	return price;
    	
        }
    
    
    
    
    public static String processNaaptol(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans =  new Elements();
        //Elements spans = doc.select("span[class=offer-price]");
        // price of a product
        spans.add(doc.select("span[class=offer-price]").get(0));
        String data = spans.text();
        data = data.replaceAll("[^0-9+]", "");
        data= StringUtils.substringBefore(data, "+");
       // System.out.println(data);
        return data;
        //<span class="offer-price"><span class="rs"><!--Rs.--></span>2,499 <span class="ship-price">+ 99 Shipping</span></span>
        }
    
    
    
    public static String processManiacstore(String URL) throws SQLException, IOException{
    	HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.get(URL);
        List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@id,'product_price')]");
        WebElement elem = listTh.get(0);
            String price = elem.getText().replaceAll("[^0-9.]", "");           
            //System.out.println(price);
        
            return price;
        //  <div class="price special-price">Selling Price: <span>8,500.00</span></div>
        }

    public static String processgadgets360(String URL) throws SQLException, IOException{
    	HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.get(URL);
        List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'selling_price')]");
        WebElement elem = listTh.get(0);
            String price = elem.getAttribute("content").replaceAll("[^0-9.]", "");           
           // System.out.println(price);
        
            return price;
        //  <div class="price special-price">Selling Price: <span>8,500.00</span></div>
        }
    public static String processBagittoday(String URL) throws SQLException, IOException{
    	HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.get(URL);
        List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'main_price')]");
        WebElement elem = listTh.get(0);
            String price = elem.getText().toString().replaceAll("[^0-9.]", "");              
           // System.out.println(price);
        
            return price;
        }
 
  
    public static void insertUpdatedPrice(float price, String website, String resolved_url,JDBCConnection con ){
        Statement stmt1;
        String Updatequery;
        if(!website.equalsIgnoreCase("flipkart"))
             Updatequery = " Update msp_electronics set latest_temp_prices = "+price+" where  website = '"+website+"' and resolved_url = '"+resolved_url+"'" ;
        else
        	 Updatequery = " Update msp_electronics set latest_temp_prices = "+price+" where  website = '"+website+"' and flipkart_product_id = '"+resolved_url+"'" ;
          //  stmt1.executeUpdate(Updatequery);
            con.upsertData(Updatequery, null);
        
    }
}
