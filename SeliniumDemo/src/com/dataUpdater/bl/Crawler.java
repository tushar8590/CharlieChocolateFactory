package com.dataUpdater.bl;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.mysql.jdbc.Connection;
 
 
public class Crawler {
    
    static{
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }
	//public static DB db = new DB();
	private static String host = "jdbc:mysql://localhost:3306/aapcompare_test";
	private static String userName = "root";
	private static String password = "";

	private static JDBCConnection con;
	
	public static void main(String[] args) throws SQLException, IOException {
		
		List imgUrl = new ArrayList();
		ResultSet rs = null;
		try{

			// Load the Driver class. 
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver here.
			//Create the connection using the static getConnection method
			//con = (Connection) DriverManager.getConnection (host,userName,password);
			con = JDBCConnection.getInstance();
			//con.setAutoCommit(false);
			String query ="SELECT website, url, resolved_url,section FROM msp_electronics WHERE resolved_url is not null and website IN ('shopclues','flipkart','snapdeal','indiatimes','naaptol','saholic','theitdepot') order by website LIMIT 100";

			rs = con.executeQuery(query);


			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
			while(rs.next())
			{
				try {
				String websiteName=rs.getString("website");
				String mspURL=rs.getString("url");
				String wesiteURL=rs.getString("resolved_url");

				//System.out.println(mspURL+" ----- " +websiteName+"-------"+wesiteURL);
				//img = "https://d1nfvnlhmjw5uh.cloudfront.net/4340-silver-1-desktop-zoom.jpg";
				callProcessMethod(websiteName,wesiteURL);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}

			}  
		


		//db.runSql2("TRUNCATE Record;");

		//String URl = "http://www.shopclues.com/kenxinda-k1-black-2.html";
		//String URl = "http://www.shopclues.com/apple-iphone-6s-64gb-space-grey-7.html";
		//String URl = "http://www.infibeam.com/Mobiles/asus-zenfone-max-zc550kl-16-gb/P-mobi-24537447317-cat-z.html";
		//String URl = "http://www.infibeam.com/Mobiles/apple-iphone-6s/P-mobi-52685543939-cat-z.html#variantId=P-mobi-36645443799";
		//String URl = "https://paytm.com/shop/p/nokia-lumia-530-black-NOKIA_LUMIA530_4GB_DARKGREY_21246";
		//String URl = "https://paytm.com/shop/p/apple-iphone-6s-plus-64-gb-silver-MOBAPPLE-IPHONETREN13033737E20160";
		// String URl = "http://shopping.indiatimes.com/mobiles/apple/apple-iphone-6s-16gb-space-grey/25001/p_B5537272";
		// String URl = "http://www.homeshop18.com/samsung-galaxy-j7-dual-sim-mobile-phone/mobiles/mobile-phones/product:33224395/cid:3027/?pos=2";
		 //String URl = "https://www.ebay.in/pdt/Apple-iPhone-5s-1/172629175";
		 //String URl = "https://www.theitdepot.com/details-Dell+Inspiron+13+7359+13.3inch+2-in-1+Laptop+(Core+i5-6200U,+8GB,+500B,+Windows+10,+Touch)_C27P26351.html";
		 //String URl = "https://www.theitdepot.com/details-Philips+IN-RR216-N+Radio_P23309.html";
		//String URl = "http://www.naaptol.com/mobile-handsets/n/p/12540935.html";
		//String URl = "http://www.naaptol.com/mobile-handsets/swipe-4g-mobile/p/12536832.html";
		//String URl = "http://www.saholic.com/mobile-phones/samsung-galaxy-j5-8gb-1015020";

		//String URl = "http://www.askmebazaar.com/buy/intex-aqua-desire-hd-black-mobile-2081241";
		
		
	}
	
	public static void callProcessMethod(String website, String URL) throws SQLException, IOException{

		String URl = URL;

		String storeName = website;
		String price = "";
		System.out.println(website);
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
		}else{
			
		}
		  float priceFloat = Float.parseFloat(price);
		insertUpdatedPrice( priceFloat,  website, URL );
	}

	public static String processSD(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		spans.add(doc.select("span[itemprop=price]").get(0));
		// recommended % of a product
		spans.add(doc.select("span[class=unitDigit]").get(0));
		System.out.println(spans.text());
		return spans.text();
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
		//<span id="priceblock_saleprice" class="a-size-medium a-color-price"><span class="currencyINR">&nbsp;&nbsp;</span> 6,999.00</span>	}
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
		}

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
		Document doc = Jsoup.connect(URL).get();
		Elements spans =  new Elements();

		// price of a product
		spans.add(doc.select("span[itemprop=price]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
		return data;
		//<span ng-if="!product.product.isOnlyCarCategory">Buy for Rs 4,987</span>
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
		
		}
	
	
	public static String processEbay(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		spans.add(doc.select("div[itemprop=price]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9]", "");
		System.out.println(data);
		return data;
		//<div class="topPriceRange" itemprop="price"> Rs. 19,699</div>
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
		//	<div class="price special-price">Selling Price: <span>8,500.00</span></div>
		}
	
	public static void insertUpdatedPrice(float price, String website, String resolved_url ){
	    Statement stmt1;
			String Updatequery = " Update msp_electronics set latest_temp_prices = "+price+" where  website = '"+website+"' and resolved_url = '"+resolved_url+"'" ;
		  //  stmt1.executeUpdate(Updatequery);
			con.upsertData(Updatequery, null);
		
	}
}