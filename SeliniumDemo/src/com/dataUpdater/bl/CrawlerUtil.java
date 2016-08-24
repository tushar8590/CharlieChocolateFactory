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
            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(price);
            while (m.find()) {
                price = m.group();
            }
           // price = price.replaceAll("[^0-9].", "");
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
        if(!storeName.equalsIgnoreCase("paytm"))
            price = price.replaceAll("[^0-9].", "");
        
          float priceFloat = Float.parseFloat(price);
        insertUpdatedPrice( priceFloat,  website, URL, con );
    }

    public static String processSD(String URL) throws SQLException, IOException{
        Document doc = Jsoup.connect(URL).get();
       
        // price of a product
        
        
       /* if(doc.select("div[class=price-dtls]").first().attr("sp").toString()!= null){
            
             price = doc.select("div[class=price-dtls]").first().attr("sp").toString(); 
            }else{
                price = doc.select("span[class=pdp-e-i-FINAL]").get(0).toString();
        }*/
        // recommended % of a product
        //spans.add(doc.select("span[class=unitDigit]").get(0));
       System.out.println(URL);
        HtmlPage    page = webClient.getPage(URL);
    	HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'payBlkBig')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("\\D+", "");   
    	//System.out.println(price);

    	if( page.getByXPath("//span[contains(@itemprop,'ratingValue')]").size() > 0)
    	{
    		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//span[contains(@itemprop,'ratingValue')]").get(0);
    		String rating = elemRating.getTextContent();   
    		//System.out.println(rating);
    	}

    	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
    		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		//System.out.println(shipping);
    	}

    	if(page.getByXPath("//span[contains(@class,'marR10 pincode-emi electronics')]").size() > 0){
    		HtmlSpan elemEmi = (HtmlSpan) page.getByXPath("//span[contains(@class,'marR10 pincode-emi electronics')]").get(0);
    		String emi = elemEmi.getTextContent().replaceAll("\\D+", "");    
    	//	System.out.println(emi);
    	}

    	if(page.getByXPath("//strong[contains(@class,'stock')]").size() > 0){
    		HtmlStrong elemEmi = (HtmlStrong) page.getByXPath("//strong[contains(@class,'stock')]").get(0);
    		String emi = elemEmi.getTextContent();  
    		//System.out.println(emi);
    	}
      //  System.out.println(price);
        return price;
    }

    public static String processFK(String URL){
        Document doc;
        try {
            doc = Jsoup.connect(URL).get();
        
        
        Elements spans = new Elements();

        // price of a product
        spans.add(doc.select("span[class=selling-price omniture-field]").get(0));
        //spans.add(doc.select("span[class=unitDigit]").get(0));
        String data = spans.text();
        data = data.replaceAll("[^0-9].", "");
       // System.out.println(data);
        return data;
        //<span class="selling-price omniture-field" data-omnifield="eVar48" data-evar48="34999">Rs. 34,999</span>
        }catch (IOException e) {
            System.out.println("Flipkart "+e.getMessage());
            return "0";
        }
    }

    
            // need to correct this method for id--- wild card needs to be implemented
    public static String processAmazon(String URL) {
        
        //<span id="priceblock_saleprice" class="a-size-medium a-color-price"><span class="currencyINR">&nbsp;&nbsp;</span> 6,999.00</span> }
        HtmlPage page;
        try {
            page = webClient.getPage(URL);
        
        HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@id,'priceblock_ourprice')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9.]", "");  
    	//System.out.println(price);

    	if( page.getByXPath("//span[contains(@class,'a-icon-alt')]").size() > 0)
    	{
    		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//*[@id='reviewStarsLinkedCustomerReviews']/i/span").get(0);
    		String rating = elemRating.getTextContent().trim();   
    		System.out.println(rating);
    	}

    	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
    		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		//System.out.println(shipping);
    	}

    	if(page.getByXPath("//div[contains(@id,'inemi_feature_div')]").size() > 0){
    		HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@id,'inemi_feature_div')]").get(0);
    		String emi = elemEmi.getTextContent().trim();    
    		int quit_position = emi.indexOf("per month");
    		emi = emi.substring(0, quit_position);
    		
    		//System.out.println(emi);
    	}

    	if(page.getByXPath("//span[contains(@class,'a-size-medium a-color-success')]").size() > 0){
    		HtmlSpan elemStock = (HtmlSpan) page.getByXPath("//span[contains(@class,'a-size-medium a-color-success')]").get(0);
    		String stock = elemStock.getTextContent().trim();  
    		//System.out.println(stock);
    	}
    	
    	return price;
        }
        catch (FailingHttpStatusCodeException | IOException e) {
         System.out.println("Amazon "+e.getMessage());
         return "0";
        }
    }
    
    public static String processShopClues(String URL) throws SQLException, IOException{
        
        

        HtmlPage    page = webClient.getPage(URL);
    	HtmlDivision elemPrice = (HtmlDivision) page.getByXPath("//div[contains(@class,'price')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9.]", "");  
    	System.out.println(price);

    	if( page.getByXPath("//span[contains(@class,'a-icon-alt')]").size() > 0)
    	{
    		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//*[@id='reviewStarsLinkedCustomerReviews']/i/span").get(0);
    		String rating = elemRating.getTextContent().trim();   
    		//System.out.println(rating);
    	}

    	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
    		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		//System.out.println(shipping);
    	}

    	if(page.getByXPath("//div[contains(@class,'emi_strt')]").size() > 0){
    		HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@class,'emi_strt')]").get(0);
    		String emi = elemEmi.getTextContent();    
    		//System.out.println(emi);
    	}

    	if(page.getByXPath("//span[contains(@class,'strong in-stock')]").size() > 0){
    		HtmlSpan elemStock = (HtmlSpan) page.getByXPath("//span[contains(@class,'strong in-stock')]").get(0);
    		String stock = elemStock.getTextContent().trim();  
    		//System.out.println(stock);
    	}
    	return price;
        }

    public static String processInfiBeam(String URL) throws SQLException, IOException{
        
        HtmlPage    page = webClient.getPage(URL);
        HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'price')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9.]", "");  
    	System.out.println(price);

    	if( page.getByXPath("//meta[contains(@itemprop,'ratingValue')]").size() > 0)
    	{
    		HtmlMeta elemRating = (HtmlMeta) page.getByXPath("//meta[contains(@itemprop,'ratingValue')]").get(0);
    		String rating = elemRating.getAttribute("content");   
    		//System.out.println(rating);
    	}//

    	if(page.getByXPath("//span[contains(@class,'shipping_duration')]").size() > 0){
    		HtmlSpan elemShipping = (HtmlSpan) page.getByXPath("//span[contains(@class,'shipping_duration')]").get(0);
    		String shipping = elemShipping.getTextContent();   
    		//System.out.println(shipping);
    	}

    	if(page.getByXPath("//span[contains(@class,'emi-value')]").size() > 0){
    		HtmlSpan elemEmi = (HtmlSpan) page.getByXPath("//span[contains(@class,'emi-value')]").get(0);
    		String emi = elemEmi.getTextContent();    
    		//System.out.println(emi);
    	}

    	if(page.getByXPath("//span[contains(@class,'strong in-stock')]").size() > 0){
    		HtmlSpan elemStock = (HtmlSpan) page.getByXPath("//span[contains(@class,'strong in-stock')]").get(0);
    		String stock = elemStock.getTextContent().trim();  
    		//System.out.println(stock);
    	}
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
        System.out.println(price);
        return price;
      
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
			//System.out.println(price);
			
			
			HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//span[contains(@itemprop,'reviewCount')]").get(0);
			String rating = elemRating.getTextContent().replaceAll("\\D+", "");   
			//System.out.println(rating);
			
			
			HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
			String shipping = elemShipping.getTextContent();   
			//System.out.println(shipping);
			
			HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpemi')]").get(0);
			String emi = elemEmi.getTextContent().replaceAll("\\D+", "");  
			//System.out.println(emi);
			
        
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
        
       
       // driver.close();
        HtmlPage    page = webClient.getPage(URL);
            HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@id,'hs18Price')]").get(0);
        	String price = elemPrice.getTextContent().replaceAll("\\D+", "");   
        	//System.out.println(price);

        	if( page.getByXPath("//span[contains(@class,'product_rating')]").size() > 0)
        	{
        		HtmlSpan elemRating = (HtmlSpan) page.getByXPath("//span[contains(@class,'product_rating')]").get(0);
        		String rating = elemRating.getTextContent().replaceAll("\\D+", "");   
        		//System.out.println(rating);
        	}

        	if(page.getByXPath("//div[contains(@class,'pdpshipping')]").size() > 0){
        		HtmlDivision elemShipping = (HtmlDivision) page.getByXPath("//div[contains(@class,'pdpshipping')]").get(0);
        		String shipping = elemShipping.getTextContent();   
        		//System.out.println(shipping);
        	}

        	if(page.getByXPath("//span[contains(@class,'easyEMI WebRupee')]").size() > 0){
        		HtmlSpan elemEmi = (HtmlSpan) page.getByXPath("//span[contains(@class,'easyEMI WebRupee')]").get(0);
        		String emi = elemEmi.getTextContent();  
        		//System.out.println(emi);
        	}

        	if(page.getByXPath("//strong[contains(@class,'stock')]").size() > 0){
        		HtmlStrong elemEmi = (HtmlStrong) page.getByXPath("//strong[contains(@class,'stock')]").get(0);
        		String emi = elemEmi.getTextContent();  
        		//System.out.println(emi);
        	}
        	return price;
        
        }
    
    
    public static String processEbay(String URL) throws SQLException, IOException{
        HtmlPage    page = webClient.getPage(URL);
        HtmlSpan elemPrice = (HtmlSpan) page.getByXPath("//span[contains(@class,'notranslate')]").get(0);
    	String price = elemPrice.getAttribute("content");  
    	//System.out.println(price);
    	return price;
    	
        }
    
    public static String processTheitdepot(String URL) throws SQLException, IOException{
        
        //<span class="price">Rs.71509/-</span>
        
        HtmlPage 	page = webClient.getPage(URL);

    	
    	HtmlDivision elemPrice = (HtmlDivision) page.getByXPath("//div[contains(@class,'price-box')]").get(0);
    	String price = elemPrice.getTextContent().replaceAll("[^0-9]", "");  
    	System.out.println(price);
    	
    	if(page.getByXPath("//div[contains(@class,'col-sm-3 price-box')]").size() > 0){
    		HtmlDivision elemEmi = (HtmlDivision) page.getByXPath("//div[contains(@class,'col-sm-3 price-box')]").get(0);
    		String emi = elemEmi.getTextContent().replaceAll("[^0-9]", "");    
    	//	System.out.println(emi);
    	}
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
