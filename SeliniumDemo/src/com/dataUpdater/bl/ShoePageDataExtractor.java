package com.dataUpdater.bl;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.dataUpdater.model.Product;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class ShoePageDataExtractor implements VendorDataUpdater, Runnable {
    private List<String> urls;
    private HtmlUnitDriver driver;
    private String mode;
    private Product product;
    private String vendor;
    private JDBCConnection conn;
    public boolean deferFlag = false;
    
    public ShoePageDataExtractor(List<String> urls, Product product, HtmlUnitDriver driver, String mode,
        String vendor) {
        this.urls = urls;
        // this.driver = driver;
        this.driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
        this.mode = mode;
        this.product = product;
        this.vendor = vendor;
    }
    
    public ShoePageDataExtractor(List<String> urls, HtmlUnitDriver driver, String mode,
        String vendor) {
        this.urls = urls;
        this.driver = driver;
        this.mode = mode;
        this.vendor = vendor;
    }
    
    public boolean processData() {
        
        System.out.println(product.getProductId());
        
        if (vendor.equalsIgnoreCase("amazon.in")) {
            int size = urls.size();
            int i = 0;
            for (String url : urls) {
                try {
                    i++;
                 
                    driver.get(url);
                    product.setProductURL(url + "?tag=aapcompare0f-21");
                    product.setProductWebsite("Amazon");
                    
                    String title = driver.findElement(
                        By.xpath("//*[@id='productTitle']")).getText();
                    
                    
                    product.setProductModel(title);
                    System.out.println(title);
                    String price ="";
                    if(driver.findElements(By.xpath("//*[@id='priceblock_ourprice']")).size() > 0)
                         price = driver.findElement(By.xpath("//*[@id='priceblock_ourprice']")).getText();
                    else
                        price = driver.findElement(By.xpath("//*[@id='priceblock_saleprice']")).getText();
                        
                    product.setProductPrice(price);
                    System.out.println(price);
                    
                    /*
                     * String starRating = driver
                     * .findElement(
                     * By.cssSelector("#acrPopover > span.a-declarative > a")).toString();
                     * product.setProductRating(starRating);
                     * System.out.println(starRating);
                     */
                    
                    if (mode.equalsIgnoreCase("insert")) {
                        insertData();
                        break;
                    }
                    else {
                        updateData();
                        break;
                    }
                }
                catch (Exception e) {
                    System.out.println("Data Not Available");
                    if (i == size)
                        logError(vendor, url, e.getMessage());
                    
                }
                finally {
                    // driver.close();
                  //  driver.quit();
                }
            }
            
        }
        if (vendor.equalsIgnoreCase("ebay.in")) {
            int size = urls.size();
            int i = 0;
            for (String url : urls) {
                try {
                    i++;
                 
                    driver.get(url);
                    product.setProductURL(url + "?tag=aapcompare0f-21");  // change this
                    product.setProductWebsite("Amazon");
                    
                    String title = driver.findElement(
                        By.xpath("//*[@id='itemTitle']']")).getText();
                    
                    
                    product.setProductModel(title);
                    System.out.println(title);
                    String price ="";
                    if(driver.findElements(By.xpath("//*[@id='prcIsum']")).size() > 0)  // change this
                         price = driver.findElement(By.xpath("//*[@id='prcIsum']']")).getText();
                    else
                        price = driver.findElement(By.xpath("//*[@id='priceblock_saleprice']")).getText();
                        
                    product.setProductPrice(price);
                    System.out.println(price);
                    
                    /*
                     * String starRating = driver
                     * .findElement(
                     * By.cssSelector("#acrPopover > span.a-declarative > a")).toString();
                     * product.setProductRating(starRating);
                     * System.out.println(starRating);
                     */
                    
                    if (mode.equalsIgnoreCase("insert")) {
                        insertData();
                        break;
                    }
                    else {
                        updateData();
                        break;
                    }
                }
                catch (Exception e) {
                    System.out.println("Data Not Available");
                    if (i == size)
                        logError(vendor, url, e.getMessage());
                    
                }
                finally {
                    // driver.close();
                  //  driver.quit();
                }
            }
            
        }
        return deferFlag;
    }
    
    @Override
    public boolean insertData() {
        
        boolean flag = false;
        System.out.println("Into the insert");
        if (this.product != null) {
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
            String sql = SQLQueries.insertShoeMappingData;
            
            flag = conn.upsertData(sql, params);
            
            sql = SQLQueries.updateShoeMappingData;
            params.clear();
            params.add(product.getProductId());
            flag = conn.upsertData(sql, params);
            System.out.println("Inserted for " + product.getProductId());
            conn.closeConnection();
            
            deferFlag = true;
            flag = true;
            System.out.println(params);
        }
        
        return flag;
        
    }
    
    @Override
    public boolean updateData() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean logError(String vendor, String url, String errorMsg) {
        boolean flag = false;
        if (this.product != null) {
            conn = JDBCConnection.getInstance();
            List<String> params = new ArrayList<String>();
            params.add(product.getProductId());
            params.add(vendor);
            params.add(url);
            params.add(errorMsg);
            String sql = SQLQueries.logElecUnmapped;

        }
        return flag;
    }
    
}
