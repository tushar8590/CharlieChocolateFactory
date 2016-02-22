package com.dataUpdater.bl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class MSPSpecLoader {
    
    
    
    Map<String,String> urlMap;
    JDBCConnection conn;
    List<String> params;
    
    String section;
    HtmlUnitDriver driver;
    String vendorUrl;
    String deliveryTime;
    String emi;
    String cod;
    String rating;
    String image;
    String price;
    String productid;

    static{
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
       }
    
    public MSPSpecLoader(){
        conn = JDBCConnection.getInstance();
        params = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        System.out.println("Starting at "+new Timestamp(new Date().getTime()));
        MSPSpecLoader obj = new MSPSpecLoader();
       // System.out.println(obj.getUrlMap().size());
        obj.getDataFromMap(obj.getUrlMap());
        
        System.out.println("Ending  at "+new Timestamp(new Date().getTime()));
    }
    
    
    /**
     * This method will return the sno and url of the records with temp_flag = 'F'
     * @return
     */    
    public Map<String,String> getUrlMap() {
        String query = "Select sno,spec_url from  msp_product_url WHERE temp_flag = 'F' LIMIT 500";
        urlMap = new HashMap<>();
        ResultSet rs = conn.executeQuery(query, null);
        try {
            while(rs.next()){
                urlMap.put(rs.getString("sno"), rs.getString("spec_url"));
            }
        }
        catch (SQLException e) {
            
            e.printStackTrace();
        }
        System.out.println(urlMap.size());
        return urlMap;
    }
    
    
    public void getDataFromMap(Map<String,String> map){
       
        StringBuilder prdSpec = new StringBuilder();
     Set<String> keySet = map.keySet();
     for(String s:keySet){
         String v = map.get(s);
        //map.forEach((k,v) -> {
            try {
                driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
                driver.get(v);
                
                String  header = "";
                 String  key = "";
                 String  value = "";
                 String combinedVal[] =null; 
                 
                 
                 
                       for(int i=1;i<=60;i++)
                         {
                             try{
                                 
                                 String divVal = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText();
                                 if(divVal.split("\n").length > 1){ // its key - value 
                                      combinedVal = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText().split("\n");
                                      key = combinedVal[0];
                                      value = combinedVal[1];
                                      prdSpec.append(key+"|");
                                      prdSpec.append(value+";");
                                 }else{
                                     header = driver.findElement(By.xpath("/html/body/div[4]/div[3]/div["+i+"]")).getText();
                                     prdSpec.append("#"+header+";");
                                 }
                     
                             
                             }
                            
                             catch(Exception e){
                                  continue;}
        
                         }

            // if all goes well save the data   
                       //  System.out.println(prdSpec.toString());
            this.saveData(prdSpec.toString(), v);
   
            } catch (Exception e) {
                e.getMessage();
                saveSkipForNoData(v);
                
            }finally{
                driver.quit();
                prdSpec.delete(0,prdSpec.length());
            }
            
            
            
            
            
            
            
            
                
        }
    }
    
    private void saveData(String prdSpec,String currentUrl) {
      String  query = SQLQueries.updateMSPSpec;
        if(prdSpec == null)
            prdSpec = " ";
        params.add(prdSpec);
        params.add(currentUrl);
         conn.upsertData(query, params);
        params.clear();
         System.out.println(currentUrl);
    }
    
    private void saveSkipForNoData(String currentUrl){
        System.out.println("No Data");
        String  query = SQLQueries.updateSkipForNoSpecData; 
        params.clear();
        params.add(currentUrl);
         conn.upsertData(query, params);
            params.clear();
    }
     
    
    
    
    
    
}
