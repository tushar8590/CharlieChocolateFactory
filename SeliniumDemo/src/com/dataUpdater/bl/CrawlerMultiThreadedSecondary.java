package com.dataUpdater.bl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataUpdater.bl.SelMultiThreadingDemo.DataExtractor;
import com.gargoylesoftware.htmlunit.BrowserVersion;

 
public class CrawlerMultiThreadedSecondary {
    
    
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
        
 String query ="SELECT website, url, resolved_url,section FROM msp_electronics WHERE resolved_url is not null and website IN ('infibeam','amazon','ebay','homeshop18','askmebazaar','maniacstore','gadgets360','bagittoday','snapdeal') and section IN ('mobiles')  order by section LIMIT 10";
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
                    vendorList.add(new VendorData(rs.getString("website"),rs.getString("resolved_url")));
                } else if(section_temp.equals("") || section_temp.equals(rs.getString("section"))){
                    vendorList.add(new VendorData(rs.getString("website"),rs.getString("resolved_url")));
                }
                
                
            }
            
            System.out.println(dataMap);
            
            
        }
        catch (SQLException e) {
             e.printStackTrace();
        }
        
        CrawlerMultiThreadedSecondary mst = new CrawlerMultiThreadedSecondary();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<String>> list = new ArrayList<Future<String>>();
              
        for(Map.Entry<String,List<VendorData>> dataEntry : dataMap.entrySet()){
           List<VendorData>  vd = (List<VendorData>)dataEntry.getValue();
            Callable<String> callable = mst.new  DataExtractor(vd,dataEntry.getKey());
            Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
       
        }
       
        executor.shutdown();
    }
    
    
  
    // A callable class to be called from ExecutorService
    class DataExtractor implements Callable{

        List<VendorData> vendorsDataList;  // dataMap value
        String section; // dataMap key
        //String query;
      
        
        @Override
        public Object call() throws Exception {
         
            vendorsDataList.forEach(v -> {
                VendorData obj = v;
                try {
                    CrawlerUtil.callProcessMethod(obj.getWebsite(),obj.getUrl(),con);
                }
                catch (Exception e) {
                   System.out.println(e.getMessage());
                    //e.printStackTrace();
                }
            });
              return null;
        }


        /**
         * @param vendorsDataList
         * @param section
         */
        public DataExtractor(List<VendorData> vendorsDataList, String section) {
            super();
            this.vendorsDataList = vendorsDataList;
            this.section = section;
        }
        
        
    
    }
    
    
    
    
}


//A model class for Map Value that contains the Vendor information

