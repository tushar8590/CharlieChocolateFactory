package com.dataUpdater.bl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlMeta;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlStrong;

public class CrawlerUtil {
    public static void callProcessMethod(String website, String URL, JDBCConnection con) throws SQLException, IOException{

        String URl = URL;
        
        String storeName = website;
        String price = "";
        System.out.println(website);
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
        }
        else if(storeName.equalsIgnoreCase("indiatimes"))
        {
            price = processIndiaTimes(URl);
        }//not working
        else if(storeName.equalsIgnoreCase("homeshop18")){
            price = processHomeshop18(URl);
        }
        else if(storeName.equalsIgnoreCase("ebay")){
            price = processEbay(URl);
        }
        else if(storeName.equalsIgnoreCase("theitdepot")){
            price = processTheitdepot(URl);
        }
        else if(storeName.equalsIgnoreCase("naaptol")){
            price = processNaaptol(URl);
        }
        else if(storeName.equalsIgnoreCase("saholic")){
            price = processSaholic(URl);
        }
        else if(storeName.equalsIgnoreCase("askmebazaar")){
            price = processAskmebazaar(URl);
        }
        else if(storeName.equalsIgnoreCase("maniacstore")){
            price = processManiacstore(URl);
        }
        else if(storeName.equalsIgnoreCase("gadgets360")){
            price = processgadgets360(URl);
        }
        else if(storeName.equalsIgnoreCase("bagittoday")){
            price = processBagittoday(URl);
        }        
        else{
            
        }
          float priceFloat = Float.parseFloat(price);
        insertUpdatedPrice( priceFloat,  website, URL, con );
    }

    public static String processSD(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
       
        // price of a product
        
        String price = "";
        if(doc.select("div[class=price-dtls]").first().attr("sp").toString()!= null){
            
             price = doc.select("div[class=price-dtls]").first().attr("sp").toString(); 
            }else{
                price = doc.select("span[class=pdp-e-i-FINAL]").get(0).toString();
        }
        // recommended % of a product
        //spans.add(doc.select("span[class=unitDigit]").get(0));
        System.out.println(price);
        return price;
        
    	/*HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'payBlkBig')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("\\D+", "");   
    	System.out.println(price);

    	if( page.getByXPath("//span[contains(@itemprop,'ratingValue')]").size() > 0)
    	{
    		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//span[contains(@itemprop,'ratingValue')]").get(0);
    		String rating = elemRating.getTextContent();   
    		System.out.println(rating);
    	}

    	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
    		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		System.out.println(shipping);
    	}

    	if(page.getByXPath("//span[contains(@class,'marR10 pincode-emi electronics')]").size() > 0){
    		HtmlSpan elemEmi = (HtmlSpan) page.getByXPath("//span[contains(@class,'marR10 pincode-emi electronics')]").get(0);
    		String emi = elemEmi.getTextContent().replaceAll("\\D+", "");    
    		System.out.println(emi);
    	}

    	if(page.getByXPath("//strong[contains(@class,'stock')]").size() > 0){
    		HtmlStrong elemEmi = (HtmlStrong) page.getByXPath("//strong[contains(@class,'stock')]").get(0);
    		String emi = elemEmi.getTextContent();  
    		System.out.println(emi);
    	}*/
    }

    public static String processFK(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans = new Elements();

        // price of a product
        spans.add(doc.select("span[class=selling-price omniture-field]").get(0));
        //spans.add(doc.select("span[class=unitDigit]").get(0));
        String data = spans.text();
        data = data.replaceAll("[^0-9]", "");
        System.out.println(data);
        return data;
        //<span class="selling-price omniture-field" data-omnifield="eVar48" data-evar48="34999">Rs. 34,999</span>
    }

    
            // need to correct this method for id--- wild card needs to be implemented
    public static String processAmazon(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans = new Elements();
        Elements spans1 =  doc.select("span[id=priceblock_ourprice]");
        // price of a product
        //spans.add(doc.select("span[id=color_name_0_price]").get(0));
        //spans.add(doc.select("span[class=unitDigit]").get(0));
        String data = spans1.text();
        data = data.replaceAll("[^0-9.]", "");
        System.out.println(data);
        return data;
        //<span id="priceblock_saleprice" class="a-size-medium a-color-price"><span class="currencyINR">&nbsp;&nbsp;</span> 6,999.00</span> }
        
     /*   HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@id,'priceblock_ourprice')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9.]", "");  
    	System.out.println(price);

    	if( page.getByXPath("//span[contains(@class,'a-icon-alt')]").size() > 0)
    	{
    		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//*[@id='reviewStarsLinkedCustomerReviews']/i/span").get(0);
    		String rating = elemRating.getTextContent().trim();   
    		System.out.println(rating);
    	}

    	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
    		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		System.out.println(shipping);
    	}

    	if(page.getByXPath("//div[contains(@id,'inemi_feature_div')]").size() > 0){
    		HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@id,'inemi_feature_div')]").get(0);
    		String emi = elemEmi.getTextContent().trim();    
    		int quit_position = emi.indexOf("per month");
    		emi = emi.substring(0, quit_position);
    		
    		System.out.println(emi);
    	}

    	if(page.getByXPath("//span[contains(@class,'a-size-medium a-color-success')]").size() > 0){
    		HtmlSpan elemStock = (HtmlSpan) page.getByXPath("//span[contains(@class,'a-size-medium a-color-success')]").get(0);
    		String stock = elemStock.getTextContent().trim();  
    		System.out.println(stock);
    	}*/
    }
    
    public static String processShopClues(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans = new Elements();

        // price of a product
        String data = doc.select("meta[itemprop=price]").first().attr("content").toString();
        //spans.add(doc.select("span[class=unitDigit]").get(0));
        //String data = spans.text();
        data = data.replaceAll("[^0-9.]", "");
        System.out.println(data);
        return data;
        //<meta itemprop="price" content="2799.00"> 
        

    	
    	/*HtmlDivision elemPrice = (HtmlDivision) page.getByXPath("//div[contains(@class,'price')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9,]", "");  
    	System.out.println(price);

    	if( page.getByXPath("//span[contains(@class,'a-icon-alt')]").size() > 0)
    	{
    		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//*[@id='reviewStarsLinkedCustomerReviews']/i/span").get(0);
    		String rating = elemRating.getTextContent().trim();   
    		System.out.println(rating);
    	}

    	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
    		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		System.out.println(shipping);
    	}

    	if(page.getByXPath("//div[contains(@class,'emi_strt')]").size() > 0){
    		HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@class,'emi_strt')]").get(0);
    		String emi = elemEmi.getTextContent();    
    		System.out.println(emi);
    	}

    	if(page.getByXPath("//span[contains(@class,'strong in-stock')]").size() > 0){
    		HtmlSpan elemStock = (HtmlSpan) page.getByXPath("//span[contains(@class,'strong in-stock')]").get(0);
    		String stock = elemStock.getTextContent().trim();  
    		System.out.println(stock);
    	}*/
        }
// added
     // for iphone its not working fine.
    public static String processInfiBeam(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans = new Elements();

        // price of a product
        //spans.add(doc.select("span[class=price]").get(0));
        //String data = doc.select("meta[itemprop=price]").first().attr("content").toString();
        spans.add(doc.select("span[class=price").get(1));
        String data = spans.text();
        data = data.replaceAll("[^0-9.]", "");
        System.out.println(data);
        return data;
        //<meta itemprop="price" content="2799.00"> 
        
        
      /*  HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'price')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9,]", "");  
    	System.out.println(price);

    	if( page.getByXPath("//meta[contains(@itemprop,'ratingValue')]").size() > 0)
    	{
    		HtmlMeta elemRating = (HtmlMeta) page.getByXPath("//meta[contains(@itemprop,'ratingValue')]").get(0);
    		String rating = elemRating.getAttribute("content");   
    		System.out.println(rating);
    	}

    	if(page.getByXPath("//span[contains(@class,'shipping_duration')]").size() > 0){
    		HtmlSpan elemShipping = (HtmlSpan) page.getByXPath("//span[contains(@class,'shipping_duration')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		System.out.println(shipping);
    	}

    	if(page.getByXPath("//span[contains(@class,'emi-value')]").size() > 0){
    		HtmlSpan elemEmi = (HtmlSpan) page.getByXPath("//span[contains(@class,'emi-value')]").get(0);
    		String emi = elemEmi.getTextContent();    
    		System.out.println(emi);
    	}

    	if(page.getByXPath("//span[contains(@class,'strong in-stock')]").size() > 0){
    		HtmlSpan elemStock = (HtmlSpan) page.getByXPath("//span[contains(@class,'strong in-stock')]").get(0);
    		String stock = elemStock.getTextContent().trim();  
    		System.out.println(stock);
    	}
*/
        }
    
    //not working
    public static String processPaytm(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans = new Elements();

        // price of a product
        spans.add(doc.select("span[ng-if=!product.product.isOnlyCarCategory]").get(0));
        String data = spans.text();
        data = data.replaceAll("[^0-9.]", "");
        System.out.println(data);
        return data;
        //<span ng-if="!product.product.isOnlyCarCategory">Buy for Rs 4,987</span>
        }
    
    public static String processIndiaTimes(String URL) throws SQLException, IOException{
    	WebClient webClient;
    	webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);//if you don't need css
        webClient.getOptions().setJavaScriptEnabled(false);//if you don't need js
        
        String data = "";
		try {
			HtmlPage 	page = webClient.getPage(URL);
		
        
			//List<HtmlSpan> listTh =(List<HtmlSpan>) page.getByXPath("//span[contains(@class,'offerprice flt')]").get(0);
			HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'offerprice flt')]").get(0);
			String price = elemPrice.getTextContent().replaceAll("\\D+", "");   
			System.out.println(price);
			
			
			HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//span[contains(@itemprop,'reviewCount')]").get(0);
			String rating = elemRating.getTextContent().replaceAll("\\D+", "");   
			System.out.println(rating);
			
			
			HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
			String shipping = elemShipping.getTextContent();   
			System.out.println(shipping);
			
			HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpemi')]").get(0);
			String emi = elemEmi.getTextContent().replaceAll("\\D+", "");  
			System.out.println(emi);
			
        
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
		}
    
    
    //not working
    public static String processHomeshop18(String URL) throws SQLException, IOException{
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.get(URL);
        List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@id,'hs18Price')]");
        WebElement elem = listTh.get(0);
            String price = elem.getText().replaceAll("\\D+", "");           
            System.out.println(price);
        
            return price;
       
       // driver.close();
            
          /*  HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@id,'hs18Price')]").get(0);
        	String price = elemPrice.getTextContent().replaceAll("\\D+", "");   
        	System.out.println(price);

        	if( page.getByXPath("//span[contains(@class,'product_rating')]").size() > 0)
        	{
        		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//span[contains(@class,'product_rating')]").get(0);
        		String rating = elemRating.getTextContent().replaceAll("\\D+", "");   
        		System.out.println(rating);
        	}

        	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
        		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
        		String shipping = elemShipping.getTextContent();   
        		System.out.println(shipping);
        	}

        	if(page.getByXPath("//span[contains(@class,'easyEMI WebRupee')]").size() > 0){
        		HtmlSpan elemEmi = (HtmlSpan) page.getByXPath("//span[contains(@class,'easyEMI WebRupee')]").get(0);
        		String emi = elemEmi.getTextContent();  
        		System.out.println(emi);
        	}

        	if(page.getByXPath("//strong[contains(@class,'stock')]").size() > 0){
        		HtmlStrong elemEmi = (HtmlStrong) page.getByXPath("//strong[contains(@class,'stock')]").get(0);
        		String emi = elemEmi.getTextContent();  
        		System.out.println(emi);
        	}*/
        
        }
    
    
    public static String processEbay(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans = new Elements();

        // price of a product
        spans.add(doc.select("div[itemprop=price]").get(0));
        String data = spans.text();
        data = data.replaceAll("[^0-9.]", "");
        System.out.println(data);
        return data;
        //<div class="topPriceRange" itemprop="price"> Rs. 19,699</div>
        
        /*HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'notranslate')]").get(0);
    	String price = elemPrice.getAttribute("content");  
    	System.out.println(price);*/
        }
    
    public static String processTheitdepot(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans =  new Elements();

        // price of a product
        spans.add(doc.select("span[class=price]").get(1));
        String data = spans.text();
        data = data.replaceAll("[^0-9]", "");
        System.out.println(data);
        return data;
        //<span class="price">Rs.71509/-</span>
      /*  
        HtmlPage 	page = webClient.getPage(url);

    	
    	HtmlDivision elemPrice = (HtmlDivision) page.getByXPath("//div[contains(@class,'price-box')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9]", "");  
    	System.out.println(price);
    	
    	if(page.getByXPath("//div[contains(@class,'col-sm-3 price-box')]").size() > 0){
    		HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@class,'col-sm-3 price-box')]").get(0);
    		String emi = elemEmi.getTextContent().replaceAll("[^0-9]", "");    
    		System.out.println(emi);
    	}*/

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
        System.out.println(data);
        return data;
        //<span class="offer-price"><span class="rs"><!--Rs.--></span>2,499 <span class="ship-price">+ 99 Shipping</span></span>
        }
    
    
    public static String processSaholic(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans =  new Elements();
        //Elements spans = doc.select("span[class=offer-price]");
        // price of a product
        spans.add(doc.select("span[class=list-price red bold]").get(0));
        String data = spans.text();
        data = data.replaceAll("[^0-9]", "");
        System.out.println(data);
        return data;
        //<span id="sp" class="list-price red bold">11390</span>
        }
    
    public static String processManiacstore(String URL) throws SQLException, IOException{
    	HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.get(URL);
        List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@id,'product_price')]");
        WebElement elem = listTh.get(0);
            String price = elem.getText().replaceAll("[^0-9.]", "");           
            System.out.println(price);
        
            return price;
        //  <div class="price special-price">Selling Price: <span>8,500.00</span></div>
        }

    public static String processgadgets360(String URL) throws SQLException, IOException{
    	HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.get(URL);
        List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'selling_price')]");
        WebElement elem = listTh.get(0);
            String price = elem.getAttribute("content").replaceAll("[^0-9.]", "");           
            System.out.println(price);
        
            return price;
        //  <div class="price special-price">Selling Price: <span>8,500.00</span></div>
        }
    public static String processBagittoday(String URL) throws SQLException, IOException{
    	HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        driver.get(URL);
        List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@class,'main_price')]");
        WebElement elem = listTh.get(0);
            String price = elem.getText().toString().replaceAll("[^0-9.]", "");              
            System.out.println(price);
        
            return price;
        }
    public static String processAskmebazaar(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements spans =  new Elements();
        //Elements spans = doc.select("span[class=offer-price]");
        // price of a product
        spans.add(doc.select("div[class=price special-price]").get(0));
        String data = spans.text();
        data = data.replaceAll("[^0-9.]", "");
        System.out.println(data);
        return data;
        //  <div class="price special-price">Selling Price: <span>8,500.00</span></div>
        }
  
    public static void insertUpdatedPrice(float price, String website, String resolved_url,JDBCConnection con ){
        Statement stmt1;
            String Updatequery = " Update msp_electronics set latest_temp_prices = "+price+" where  website = '"+website+"' and resolved_url = '"+resolved_url+"'" ;
          //  stmt1.executeUpdate(Updatequery);
            con.upsertData(Updatequery, null);
        
    }
}
