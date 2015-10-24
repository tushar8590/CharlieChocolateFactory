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
       
        List<String> params = new ArrayList<String>();
        String id = "";
        
            while(rs.next()){
            	HtmlUnitDriver driver = null;
                try {
                	  driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
                    String urlOld = rs.getString("url");
                    MspUrlResolver.website = rs.getString("website");
                    id = rs.getString("id");
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
                   
                    StringBuilder resolvedUrl = new StringBuilder(driver.getCurrentUrl().substring(0, driver.getCurrentUrl().indexOf("?")));
                  
                    
                    if(MspUrlResolver.website.equalsIgnoreCase("amazon"))
                        resolvedUrl.append("?tag=aapcompare0f-21");
                    else if(MspUrlResolver.website.equalsIgnoreCase("flipkart"))
                        resolvedUrl.append("?affid=a123pp9aa");
                    else if(MspUrlResolver.website.equalsIgnoreCase("infibeam"))
                        resolvedUrl.append("?trackId=a12");
                    else if(MspUrlResolver.website.equalsIgnoreCase("snapdeal"))
                        resolvedUrl.append("?aff_id=37358");
                    else if(MspUrlResolver.website.equalsIgnoreCase("shopclues"))
                        resolvedUrl.append("?ID:756");
                    else if(MspUrlResolver.website.equalsIgnoreCase("indiatimes") || MspUrlResolver.website.equalsIgnoreCase("paytm"))
                        resolvedUrl =   new StringBuilder("http://clk.omgt5.com/?AID=769090&PID=11365&r=").append(resolvedUrl);
                    
                    
                    
                    String updateQUery = SQLQueries.udpateMspUResolvedUrl;
                   
                    params.add(resolvedUrl.toString());
                    params.add(id);
                   
                    if(conn.upsertData(updateQUery, params)){
                        counter ++;
                    }
                    
                    params.clear();
                    

                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    deferUpdate(params, id);
                    continue;
                }finally{
                    //conn.closeConnection();
                	 driver.close(); 

                }
                
                
            }
            
            System.out.println("Ending at "+new Timestamp(new Date().getTime()));
            System.out.println("Data Inserted for "+counter+"  items");
        
           
    }

    private void deferUpdate(List<String> params, String id) {
        String updateQUery = SQLQueries.udpateMspUResolvedUrlDeffered;
        params.add(id);
        conn.upsertData(updateQUery, params);
        params.clear();
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
