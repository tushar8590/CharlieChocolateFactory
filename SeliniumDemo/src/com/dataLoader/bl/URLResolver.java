package com.dataLoader.bl;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;




import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class URLResolver {

	public URLResolver() {
		
	}
	JDBCConnection conn;

	public static void main(String[] args) throws Exception {
		URLResolver ur = new URLResolver();
		ur.populateList();
		
	}
	static String website;
	static{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	@SuppressWarnings(value = { "" })
	public void populateList() throws Exception{
		
		int counter = 1;
		conn = JDBCConnection.getInstance();
		//ResultSet rs = conn.executeQuery(SQLQueries.getURLsPCIFeed, null);
		ResultSet rs = conn.executeQuery(SQLQueries.getPartiallyResolvedURLS, null);
		
		System.out.println("Starting at "+new Timestamp(new Date().getTime()));
			while(rs.next()){
				try {
					String urlOld = rs.getString("url");
					URLResolver.website = rs.getString("website");
					String id = rs.getString("id");
					HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.CHROME);
					//WebDriver driver = new FirefoxDriver();
					
						driver.get(urlOld);
						WebElement elm = driver.findElement(By.xpath("//*[@id='redirect-div']/p[4]/a"));
						//System.out.println(elm.getAttribute("href"));
						
						
						
					/*synchronized(driver){
						driver.wait(15000);
					}*/
						
					//WebDriverWait wait = new WebDriverWait(driver, 15);
					//System.out.println(driver.getCurrentUrl());
					
					(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
					    public Boolean apply(WebDriver d) {
					    	boolean flag = false;
					    	//System.out.println(d.getCurrentUrl());
					    	if(d.getCurrentUrl().startsWith("http://www."+URLResolver.website)){
					    		
					    		flag = true;
					    	}else if(d.getCurrentUrl().startsWith("https://"+URLResolver.website)){
					    		flag = true;
					    		
					    	}else
					    	{
					    		flag = true;
					    		
					    	}
					    return flag;
					    }
					});
					
					
					

					String url = elm.getAttribute("href");
					driver.close(); driver.quit();
					
					
					// save it
					
					String sql = SQLQueries.insertproduct_pci_url_temp;
					
					List<String> params = new ArrayList<String>();
					params.add(id);
					params.add(URLResolver.website);
					params.add(url);
					if(conn.upsertData(sql, params)){
						System.out.println(id);
						System.out.println("Inserted " +url);
						counter ++;
					}
					
					params.remove(2);
					
					if(conn.upsertData(SQLQueries.updatePCIFeedForUrlMapping, params)){
						//counter++;
					}
					
					//conn.closeConnection();
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					//conn.closeConnection();
				}
			}
			
			System.out.println("Ending at "+new Timestamp(new Date().getTime()));
			System.out.println("Data Inserted for "+counter+"  items");
		
	}

}





