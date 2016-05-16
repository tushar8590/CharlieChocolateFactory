package com.dataUpdater.bl;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

 
public class CrawlerMultiThreaded {
    
    
    static{
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }
    

    private static JDBCConnection con;
    private static  ResultSet rs = null;
    private static Map<String,List<VendorData>> dataMap = null;
    
    public static void main(String[] args) {
      
        // Lets create the object on basis of section and VendirDAta Object
        con = JDBCConnection.getInstance();
        String query ="SELECT website, url, resolved_url,section FROM msp_electronics WHERE resolved_url is not null and website IN ('shopclues','flipkart','snapdeal','indiatimes','naaptol','saholic','theitdepot') order by section LIMIT 100";
        rs = con.executeQuery(query);
        dataMap = new HashMap<String,List<VendorData>>();
        
        // a temp variable to track the current websute/vdendo says that its secret.
        
        String section_temp = "";
        List<VendorData> vendorList = new ArrayList<VendorData>();
        try {
            while(rs.next()){
                if(!section_temp.equals(rs.getString("section"))){
                    section_temp = rs.getString("section");
                    dataMap.put(section_temp, vendorList); 
                    vendorList.clear();
                    vendorList.add(new VendorData(rs.getString("website"),rs.getString("url")));
                } else if(section_temp.equals("") || section_temp.equals(rs.getString("section"))){
                    vendorList.add(new VendorData(rs.getString("website"),rs.getString("url")));
                }
                
                
            }
            
            System.out.println(dataMap);
            
            
        }
        catch (SQLException e) {
             e.printStackTrace();
        }
        
    }
    
    
    /*private static getUrls(){
        
        
    }*/
    
    
    // A callable class to be called from ExecutorService
    class DataExtractor implements Callable{

        List<VendorData> vendorsDataList;  // dataMap value
        String section; // dataMap key
        //String query;
      
        
        @Override
        public Object call() throws Exception {
              return null;
        }
        
        
    
    }
    
    
    
    
}


//A model class for Map Value that contains the Vendor information

class VendorData{
    private String website;
    private String url;
    
    
    /**
     * @param website
     * @param url
     */
    public VendorData(String website, String url) {
        super();
        this.website = website;
        this.url = url;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VendorData other = (VendorData) obj;
        if (url == null) {
            if (other.url != null)
                return false;
        }
        else if (!url.equals(other.url))
            return false;
        if (website == null) {
            if (other.website != null)
                return false;
        }
        else if (!website.equals(other.website))
            return false;
        return true;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
}