package com.dataUpdater.bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataUpdater.model.Product;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class TestDataUpdater implements VendorDataUpdater {

	
	private List<String> urls;
	private HtmlUnitDriver driver;
	private String mode;
    private Product product;
	private String vendor;
    private JDBCConnection conn; 
	
	public TestDataUpdater(List<String> urls,Product product, HtmlUnitDriver driver, String mode,
			String vendor) {
		this.urls = urls;
		//this.driver = driver;
		this.driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
		this.mode = mode;
		this.product = product;
		this.vendor = vendor;
	}
	
	
	public TestDataUpdater(List<String> urls, HtmlUnitDriver driver, String mode,
			String vendor) {
		this.urls = urls;
		this.driver = driver;
		this.mode = mode;
		this.vendor = vendor;
	}

	public void processData() {
		System.out.println(product.getProductId());
		if (vendor.equalsIgnoreCase("amazon.in")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try {
				i++;
				System.out.println("Getting the data");
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("Amazon");
				String title = driver.findElement(
						By.cssSelector("#productTitle")).getText();
				product.setProductModel(title);
				System.out.println(title);

				String price = driver.findElement(
						By.cssSelector("#olp_feature_div > div > span > span"))
						.getText();
				product.setProductPrice(price);
				System.out.println(price);

				/*String starRating = driver
						.findElement(
								By.cssSelector("#acrPopover > span.a-declarative > a")).toString();
				product.setProductRating(starRating);
				System.out.println(starRating);*/

				if (mode.equalsIgnoreCase("insert")) {
					insertData();break;
				} else{
					updateData(); break;
				}	
			} catch (Exception e) {
				System.out.println("Data Not Available");
				if(i==size)
				logError(vendor,url,e.getMessage());
			}finally{
				//driver.close();
			}
			}

		}
		
		if (vendor.equalsIgnoreCase("infibeam")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println(this.getClass().getName());
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("Infibeam");
				String title = driver.findElement(By.cssSelector("#title > h1")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#price-after-discount > span.price")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				try{
				String starRating = driver.findElement(By.cssSelector("#review_and_rating_summary > span.rating-star > img:nth-child(1)")).getAttribute("alt").toString();
				product.setProductRating(starRating);
				System.out.println(starRating);
				}catch(Exception e){}
				
				if(mode.equalsIgnoreCase("insert")){
					insertData();break;
					
				}else{
					updateData(); break;
				}
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
		}
		}
		
		if (vendor.equalsIgnoreCase("homeshop18")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("homeshop18");
				System.out.println(url);
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("HomeShop18");
				
				String title = driver.findElement(By.cssSelector("#productTitleInPDP")).getText().toString();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#hs18Price")).getText().toString();
				product.setProductPrice(price);
				System.out.println(price);
				
/*				String starRating = driver.findElement(By.cssSelector("#avgProductRatingPDPDiv > span")).getText();
				product.setProductRating(starRating);
				System.out.println(starRating);
				
				*/
				try{
				String stock = driver.findElement(By.cssSelector("#btnBuyNow ")).getText().toString();
				product.setProductStock(stock);
				System.out.println(stock);
				}catch(Exception e){}
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("Data Not Available");
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}
		
		if (vendor.equalsIgnoreCase("croma")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Croma");
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("Croma");
				
				String title = driver.findElement(By.cssSelector("#form1 > section > div.pDesc > h1")).getText().toString();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#form1 > section > div.pDesc > div.cta > h2")).getText().toString();
				product.setProductPrice(price);
				System.out.println(price);
				
				try{
				String stock = driver.findElement(By.cssSelector("#imgBN ")).getAttribute("alt").toString();
				product.setProductStock(stock);
				System.out.println(stock);
				}catch(Exception e){}
							
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}

		if (vendor.equalsIgnoreCase("ebay.in")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Ebay");
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("Ebay");
				
				String title = driver.findElement(By.cssSelector("#itemTitle")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#prcIsum")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				try{
				String stock = driver.findElement(By.cssSelector("#qtySubTxt > span")).getText();
				product.setProductStock(stock);
				System.out.println(stock);
				}catch(Exception e){}
							
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}
		
		if (vendor.equalsIgnoreCase("snapdeal")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Snapdeal");
				System.out.println(url);
				driver.get(url);
				
				product.setProductURL(url);
				product.setProductWebsite("Snapdeal");
				String title = driver.findElement(By.cssSelector("#pdp > div:nth-child(3) > div.pdpCatWrapper.pdpPage.blk.pdp3Revamp > div.productDeal-right > div.prodtitle-head > div.productTitle > div > h1")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#selling-price-id")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				try{
				String starRating =  driver.findElement(By.cssSelector("#pdp > div:nth-child(3) > div.pdpCatWrapper.pdpPage.blk.pdp3Revamp > div.productDeal-right > div.prodtitle-head > div.productTitle > div > div.lfloat > div.lfloat.pdpRatingStars")).getAttribute("ratings").toString();
				product.setProductRating(starRating);
				System.out.println(starRating);
				
			
				String stock = driver.findElement(By.cssSelector("#BuyButton-1")).getText().toString();
				product.setProductStock(stock);
				System.out.println(stock);
				}catch(Exception e){}

				
				//System.out.println("Offers = "+driver.findElement(By.cssSelector("#pdp > div:nth-child(3) > div.pdpCatWrapper.pdpPage.blk.pdp3Revamp > div.productDeal-right > div.ClsOfferContainer > div.ClsPromoContainer > div.ClsWrapOverflow > ")));
				
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("Data Not Available");
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}
		
		if (vendor.equalsIgnoreCase("indiatimes")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Indiatimes");
				System.out.println(url);
				driver.get(url);
				
				product.setProductURL(url);
				product.setProductWebsite("IndiatimesShopping");
				
				String title = driver.findElement(By.cssSelector("#contentbody > div.content-wrap > div.productdetailwrapper > div.productcontainer.pdpboxline.clear > div.flt.productcolumone.zur > h1")).getText().toString();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#topproductcolumtwo > div.pricetab > span.offerprice.flt")).getText().toString();
				product.setProductPrice(price);
				System.out.println(price);
				
				try{
				String stock = driver.findElement(By.cssSelector("#midproductcolumtwo > div.pdpbuynottab > span.buynowpdp.flt > a")).getText().toString();
				product.setProductStock(stock);
				System.out.println(stock);
				}catch(Exception e){
					product.setProductStock("outofstock");
				}
				try{
				String starRating = driver.findElement(By.cssSelector("#contentbody > div.content-wrap > div.productdetailwrapper > div.productcontainer.pdpboxline.clear > div.flt.productcolumone.zur > div > span > span.rating.flt > span > span")).getText().toString();
				product.setProductRating(starRating);
				System.out.println(starRating);
				}catch(Exception e){}	
				
				
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				//e.printStackTrace();
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}

		if (vendor.equalsIgnoreCase("shopclues")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Shopclues");
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("Shopclues");
				
				String title = driver.findElement(By.cssSelector("#content > div.content-helper > div.aside-site-content.left > div.site-content > div.product > form > div.product-about > div.name > h1")).getText().toString();
				product.setProductModel(title);
				System.out.println(title);
				
				String prodId= driver.findElement(By.cssSelector("#content > div.content-helper > div.aside-site-content.left > div.site-content > div.product > form > div.product-about > div.name > span > span")).getText();
				prodId = prodId.replace("SCIN : ", "");
				
				String price = driver.findElement(By.cssSelector("#line_discounted_price_"+prodId)).getText().toString();
				product.setProductPrice(price);
				System.out.println(price);
				
				try{
				String stock =  driver.findElement(By.cssSelector("#in_stock_info_"+prodId)).getText();

				product.setProductStock(stock);
				System.out.println(stock);
				
				}catch(Exception e){
					//String outofStock = driver.findElement(By.cssSelector("#out_of_stock_info_"+prodId+" > font")).getText();
					product.setProductStock("outofStock");
				}	
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				//e.printStackTrace();
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}
		
		
		if (vendor.equalsIgnoreCase("rediff")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Rediff");
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("RediffShopping");
				String title = driver.findElement(By.cssSelector("#div_prdetail_left > div > div.floatL.product_detail > div > div:nth-child(1) > h1 > span")).getText().toString();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#prod_prcs")).getText().toString();
				product.setProductPrice(price);
				System.out.println(price);

				
				try{
				String stock = driver.findElement(By.cssSelector("#div_buynow_btn > div > input")).getAttribute("value").toString();
				product.setProductStock(stock);
				System.out.println(stock);
				}catch(Exception e){
					System.out.println("out of stock");

				}
											
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				if(i==size)
					logError(vendor,url,e.getMessage());
				}
			}
		}
		if (vendor.equalsIgnoreCase("ThemobileStore")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("ThemobileStore");
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("ThemobileStore");
				String title = driver.findElement(By.cssSelector("#product_addtocart_form > div.product-shop > div.product-name > span > h1")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#product-price-15244 > span")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				
				try{
				String stock = driver.findElement(By.cssSelector("#product_addtocart_form > div.product-shop > div:nth-child(7) > p")).getText();
				product.setProductStock(stock);
				System.out.println(stock);
				}catch(Exception e){}
			
							
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				System.out.println("Data Not Available");
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}
		if (vendor.equalsIgnoreCase("Univercell")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Univercell");
				driver.get(url);
				product.setProductURL(url);
				product.setProductWebsite("Univercell");
				String title = driver.findElement(By.cssSelector("#main > div.productdetail_container > div.productbucketgroup > div > div.productdetail_leftdiv > div.productdescription > div.rightpane > div.container9 > div.ctl_aboutbrand > h1")).getText();
				product.setProductModel(title);
				System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_Price_ctl00_lblOfferPrice > span.sp_amt")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				
				try{
				String stock = driver.findElement(By.cssSelector("#instock > div")).getText();
				product.setProductStock(stock);
				System.out.println(stock);
				
				String starRating = driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_Ratings_ctl00_divAvgRat")).getText();
				product.setProductRating(starRating);
				System.out.println(starRating);
				}catch(Exception e){}
							
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				//System.out.println("Data Not Available");
				//e.printStackTrace();
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}
		if (vendor.equalsIgnoreCase("Paytm")) {
			int size = urls.size();
			int i = 0;
			for(String url : urls){
			try{
				i++;
				System.out.println("Paytm");
				driver.get(url);
				/*WebElement myDynamicElement = (new WebDriverWait(driver, 20))
			              .until(ExpectedConditions.presenceOfElementLocated(By.id("midd-container-inner")));*/
				 
				product.setProductURL(url);
				product.setProductWebsite("Paytm");
				//String title = driver.findElement(By.cssSelector("#midd-container-inner > div:nth-child(2) > div.product-details.left > div.product-title-wrapper > h2")).getText();
				//product.setProductModel(title);
				//System.out.println(title);
				
				
				String price = driver.findElement(By.cssSelector("#midd-container-inner > div:nth-child(2) > div.product-details.left > div.discraption > div.mt10 > div.left.mb10 > button:nth-child(1) > span.text")).getText();
				product.setProductPrice(price);
				System.out.println(price);
				
				String stock = driver.findElement(By.cssSelector("#product_addtocart_form > div.product-shop > div:nth-child(7) > p")).getText();
				product.setProductStock(stock);
				System.out.println(stock);
				
				String starRating = driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_Ratings_ctl00_divAvgRat")).getText();
				product.setProductRating(starRating);
				System.out.println(starRating);
			
							
				if(mode.equalsIgnoreCase("insert")){
					insertData();
					break;
				}else{
					updateData();
					break;
				}
				
			}catch(Exception e){
				//System.out.println("Data Not Available");
				//e.printStackTrace();
				if(i==size)
				logError(vendor,url,e.getMessage());
				}
			}
		}
		
	}

	@Override
	public boolean insertData() {

		boolean flag = false;
		
		if(this.product!=null){
			conn = JDBCConnection.getInstance(); 
			List<String> params = new ArrayList<String>(); 
			params.add(product.getProductId());
			params.add(product.getProductModel());
			params.add(product.getProductURL());
			params.add(product.getProductWebsite());
			params.add(product.getProductOffer());
			params.add(product.getProductPrice());
			params.add(product.getProductStock());
			params.add(product.getProductColor());
			params.add(product.getProductRating());
			String sql = SQLQueries.insertElecMultiVendorData;
			flag = conn.upsertData(sql, params);
			
			
			sql = SQLQueries.updateElecProductMaster;
			params.clear();params.add(product.getProductId());
			flag = conn.upsertData(sql, params);
			System.out.println("Inserted for "+product.getProductId());
			conn.closeConnection();
		}
		  
		  
		  
		 
		return flag;

	}

	@Override
	public boolean updateData() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean logError(String vendor,String url,String errorMsg){
		boolean flag = false;
		if(this.product!=null){
			conn = JDBCConnection.getInstance(); 
			List<String> params = new ArrayList<String>(); 
			params.add(product.getProductId());
			params.add(vendor);
			params.add(url);
			params.add(errorMsg);
			String sql = SQLQueries.logElecUnmapped;
			flag = conn.upsertData(sql, params);
			conn.closeConnection();
		}
		return flag;
	}

}
