/**
 * 
 */
package com.dataLoader.bl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * @author JN1831
 *  @date 21 Oct 2015
 *  This file is used to resolve all the urls of MSPS
 */
public class MspUrlResolver {
    
    
    JDBCConnection conn;
    static String website;
    static{
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }
    
    public void populateList() throws Exception{

        int counter = 0;
        conn = JDBCConnection.getInstance();
        //ResultSet rs = conn.executeQuery(SQLQueries.getURLsPCIFeed, null);
        ResultSet rs = conn.executeQuery(SQLQueries.getUnresolvedUrls, null);
        
        System.out.println("Starting at "+new Timestamp(new Date().getTime()));
        HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
        List<String> params = new ArrayList<String>();
            while(rs.next()){
                try {
                    String urlOld = rs.getString("url");
                    MspUrlResolver.website = rs.getString("website");
                    String id = rs.getString("id");
                  //  HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
                    driver.get(urlOld);
                   
                    (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            boolean flag = false;
                            //System.out.println(d.getCurrentUrl());
                            if(d.getCurrentUrl().startsWith("http://www."+MspUrlResolver.website)){
                                
                                flag = true;
                            }else if(d.getCurrentUrl().startsWith("https://"+MspUrlResolver.website)){
                                flag = true;
                                
                            }else
                            {
                                flag = true;
                                
                            }
                        return flag;
                        }
                    });
                    
                    
                    
                    // save it
                  //  temp 
                   
                    String resolvedUrl = driver.getCurrentUrl();
                    
                    String updateQUery = SQLQueries.udpateMspUResolvedUrl;
                   
                    params.add(resolvedUrl);
                    params.add(id);
                   
                    if(conn.upsertData(updateQUery, params)){
                        counter ++;
                    }
                    
                    params.clear();
                    

                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally{
                    //conn.closeConnection();
                    
                }
                
                
            }
            
            System.out.println("Ending at "+new Timestamp(new Date().getTime()));
            System.out.println("Data Inserted for "+counter+"  items");
        
            driver.close(); driver.quit();

    }
    
    public static void main(String[] args) {
        
        MspUrlResolver mspURL  = new MspUrlResolver();
        try {
            mspURL.populateList();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}
