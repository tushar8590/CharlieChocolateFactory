package com.dataUpdater.bl;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.mysql.jdbc.Connection;
 
 
public class Crawler {
	//public static DB db = new DB();
	private static String host = "jdbc:mysql://localhost:3306/aapcompare_test";
	private static String userName = "root";
	private static String password = "";

	private static Connection con;
	public static void main(String[] args) throws SQLException, IOException {
		
		List imgUrl = new ArrayList();
		ResultSet rs = null;
		try{

			// Load the Driver class. 
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver here.
			//Create the connection using the static getConnection method
			con = (Connection) DriverManager.getConnection (host,userName,password);
			con.setAutoCommit(false);
			String query ="SELECT website, url, resolved_url FROM msp_electronics WHERE section = 'mobiles' AND website IN ('shopclues','flipkart','snapdeal','indiatimes','ebay','naaptol','saholic','theitdepot') order by website";

			Statement stmt = (Statement) con.createStatement();


			rs = stmt.executeQuery(query);

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

				System.out.println(mspURL+" ----- " +websiteName+"-------"+wesiteURL);
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

		if(storeName.equalsIgnoreCase("snapdeal")){
			processSD(URl);
		}
		else if(storeName.equalsIgnoreCase("flipkart"))
		{
			processFK(URl);
		}//not working
		else if(storeName.equalsIgnoreCase("amazon")){
			processAmazon(URl);
		}
		else if(storeName.equalsIgnoreCase("shopclues")){
			processShopClues(URl);
		}
		else if(storeName.equalsIgnoreCase("infibeam")){
			processInfiBeam(URl);
		}//not working
		else if(storeName.equalsIgnoreCase("paytm")) {
			processPaytm(URl);
		}
		else if(storeName.equalsIgnoreCase("indiatimes"))
		{
			processIndiaTimes(URl);
		}//not working
		else if(storeName.equalsIgnoreCase("homeshop18")){
			processHomeshop18(URl);
		}
		else if(storeName.equalsIgnoreCase("ebay")){
			processEbay(URl);
		}
		else if(storeName.equalsIgnoreCase("theitdepot")){
			processTheitdepot(URl);
		}
		else if(storeName.equalsIgnoreCase("naaptol")){
			processNaaptol(URl);
		}
		else if(storeName.equalsIgnoreCase("saholic")){
			processSaholic(URl);
		}
		else{
			processAskmebazaar(URl);
		}
	}

	public static void processSD(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		spans.add(doc.select("span[itemprop=price]").get(0));
		// recommended % of a product
		spans.add(doc.select("span[class=unitDigit]").get(0));
		System.out.println(spans.text());
	}

	public static void processFK(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		spans.add(doc.select("span[class=selling-price omniture-field]").get(0));
		//spans.add(doc.select("span[class=unitDigit]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9]", "");
		System.out.println(data);
		//<span class="selling-price omniture-field" data-omnifield="eVar48" data-evar48="34999">Rs. 34,999</span>
	}

	
			// need to correct this method for id--- wild card needs to be implemented
	public static void processAmazon(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();
		Elements spans1 =  doc.select("span[id=priceblock_ourprice]");
		// price of a product
		//spans.add(doc.select("span[id=color_name_0_price]").get(0));
		//spans.add(doc.select("span[class=unitDigit]").get(0));
		String data = spans1.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
		//<span id="priceblock_saleprice" class="a-size-medium a-color-price"><span class="currencyINR">&nbsp;&nbsp;</span> 6,999.00</span>	}
	}
	
	public static void processShopClues(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		String data = doc.select("meta[itemprop=price]").first().attr("content").toString();
		//spans.add(doc.select("span[class=unitDigit]").get(0));
		//String data = spans.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
		//<meta itemprop="price" content="2799.00">	
		}

	 // for iphone its not working fine.
	public static void processInfiBeam(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		//spans.add(doc.select("span[class=price]").get(0));
		//String data = doc.select("meta[itemprop=price]").first().attr("content").toString();
		spans.add(doc.select("span[class=price").get(1));
		String data = spans.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
		//<meta itemprop="price" content="2799.00">	
		}
	
	//not working
	public static void processPaytm(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		spans.add(doc.select("span[ng-if=!product.product.isOnlyCarCategory]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
		//<span ng-if="!product.product.isOnlyCarCategory">Buy for Rs 4,987</span>
		}
	
	public static void processIndiaTimes(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans =  new Elements();

		// price of a product
		spans.add(doc.select("span[itemprop=price]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
		//<span ng-if="!product.product.isOnlyCarCategory">Buy for Rs 4,987</span>
		}
	
	
	//not working
	public static void processHomeshop18(String URL) throws SQLException, IOException{
	    HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
	    driver.get(URL);
	    List<WebElement> listTh = driver.findElementsByXPath("//span[contains(@id,'hs18Price')]");
	    WebElement elem = listTh.get(0);
            String price = elem.getText().replaceAll("\\D+", "");           
            System.out.println(price);
        
     
       
        driver.close();
		
		}
	
	
	public static void processEbay(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans = new Elements();

		// price of a product
		spans.add(doc.select("div[itemprop=price]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9]", "");
		System.out.println(data);
		//<div class="topPriceRange" itemprop="price"> Rs. 19,699</div>
		}
	
	public static void processTheitdepot(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans =  new Elements();

		// price of a product
		spans.add(doc.select("span[class=price]").get(1));
		String data = spans.text();
		data = data.replaceAll("[^0-9]", "");
		System.out.println(data);
		//<span class="price">Rs.71509/-</span>
		}
	
	
	public static void processNaaptol(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans =  new Elements();
		//Elements spans = doc.select("span[class=offer-price]");
		// price of a product
		spans.add(doc.select("span[class=offer-price]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9+]", "");
		data= StringUtils.substringBefore(data, "+");
		System.out.println(data);
		//<span class="offer-price"><span class="rs"><!--Rs.--></span>2,499 <span class="ship-price">+ 99 Shipping</span></span>
		}
	
	
	public static void processSaholic(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans =  new Elements();
		//Elements spans = doc.select("span[class=offer-price]");
		// price of a product
		spans.add(doc.select("span[class=list-price red bold]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9]", "");
		System.out.println(data);
		//<span id="sp" class="list-price red bold">11390</span>
		}
	
	public static void processAskmebazaar(String URL) throws SQLException, IOException{
		Document doc = Jsoup.connect(URL).get();
		Elements spans =  new Elements();
		//Elements spans = doc.select("span[class=offer-price]");
		// price of a product
		spans.add(doc.select("div[class=price special-price]").get(0));
		String data = spans.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
		//	<div class="price special-price">Selling Price: <span>8,500.00</span></div>
		}
}