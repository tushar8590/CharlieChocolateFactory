package com.dataUpdater.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class FetchMaxPageNumberForCategory {
	
	private static String host = "jdbc:mysql://localhost:3306/aapcompare_test";
	private static String userName = "root";
	private static String password = "";


	private static Connection con;



	private ResultSet rs;

	
	public static void main(String args[]){
		try{

			// Load the Driver class. 
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver here.

			//Create the connection using the static getConnection method
			con = DriverManager.getConnection (host,userName,password);
			con.setAutoCommit(false);

		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			String query ="SELECT category, first_page_url FROM category_main_url ";

			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();


			ResultSet rs;

			rs = stmt.executeQuery(query);

			while(rs.next())
			{
				try {
				String dbCategory =rs.getString("category");

				String url =rs.getString("first_page_url");
				
				//System.out.println("hee");

				//HtmlUnitDriver driver;
				
				//WebDriver driver;
				//driver =  new FirefoxDriver();
				//driver = new WebDriver(BrowserVersion.CHROME);
				 HtmlUnitDriver driver = new HtmlUnitDriver();
				//System.out.println(url);
				  driver.get(url);
				//  WebElement myDynamicElement = (new WebDriverWait(driver, 100))
			   //           .until(ExpectedConditions.presenceOfElementLocated(By.className("pgntn js-prdct-pgntn")));
				  String productUrl="";
				  if(driver.findElements(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[5]")).size() !=  0){
					   productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[5]")).getAttribute("href");
				  }else if(driver.findElements(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[4]")).size() !=  0) {
					   productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[4]")).getAttribute("href");
				  }	else{
					  productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[5]/div[2]/div[1]/div[49]/a[5]")).getAttribute("href");
				  }					 
						
				  //WebElement productUrl = driver.findElement(By.cssSelector("body > div.body-wrpr.clearfix > div.algn-wrpr.clearfix > div.main-wrpr.list-main > div.list-mdl > div.prdct-grid-wrpr.js-prdct-grid-wrpr > div.sctn.prdct-grid.prdct-grid--s.prdct-grid--spcftn-5.clearfix > div.pgntn.js-prdct-pgntn > div > span:nth-child(2)"));
				 String pageNumber = productUrl.substring(productUrl.indexOf(".html") - 1).replace(".html", "");
				  System.out.println(pageNumber);
					stmt1 = con.createStatement();
				  String Updatequery = " Update category_main_url set total_pages = "+pageNumber+" where  category = '"+dbCategory+"' and  first_page_url = '"+url+"'";
						  
						 
					//System.out.println(Updatequery);
				stmt1.executeUpdate(Updatequery);
				}
				catch(Exception e){
					continue;
				}
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
