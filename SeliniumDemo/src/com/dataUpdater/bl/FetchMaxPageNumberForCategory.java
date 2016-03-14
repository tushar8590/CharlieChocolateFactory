package com.dataUpdater.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class FetchMaxPageNumberForCategory {
    
    static{
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }
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
            String query ="SELECT section, first_page_url FROM category_main_url ";

            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();


            ResultSet rs;

            rs = stmt.executeQuery(query);

            while(rs.next())
            {
                
                String dbCategory =rs.getString("section");

                String url =rs.getString("first_page_url");
                
                

                //HtmlUnitDriver driver;
                
                
                 HtmlUnitDriver driver = new HtmlUnitDriver();
            
                  driver.get(url);

                  String productUrl=""; 
                  String pageNumber = "";                        
                  if(driver.findElements(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[5]")).size() !=  0){
                       productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[5]")).getAttribute("href");
                  }else if(driver.findElements(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[4]")).size() !=  0) {
                       productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[3]/div[2]/div[1]/div[49]/a[4]")).getAttribute("href");
                  } else if(driver.findElements(By.xpath("/html/body/div[4]/div[3]/div[1]/div[5]/div[2]/div[1]/div[49]/a[5]")).size() !=  0){
                      productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[5]/div[2]/div[1]/div[49]/a[5]")).getAttribute("href");
                  } else if(driver.findElements(By.xpath(" /html/body/div[4]/div[3]/div[1]/div[4]/div[2]/div[1]/div[49]/a[5]")).size() !=  0){
                      productUrl = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div[1]/div[4]/div[2]/div[1]/div[49]/a[5]")).getAttribute("href");
                  }else{
                  }
                  
                  if(!productUrl.equals("")){
                       pageNumber = productUrl.substring(productUrl.lastIndexOf("-")+1).replace(".html", "");
                  }else{
                      pageNumber = "0";
                  }
                    stmt1 = con.createStatement();
                  String Updatequery = " Update category_main_url set total_pages = "+pageNumber+" where  section = '"+dbCategory+"' and  first_page_url = '"+url+"'";
                          
                         
                stmt1.executeUpdate(Updatequery);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        }
    }

}
