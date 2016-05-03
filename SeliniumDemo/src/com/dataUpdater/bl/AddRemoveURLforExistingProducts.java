

package com.dataUpdater.bl;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Connection;
 
 
public class AddRemoveURLforExistingProducts {
	
	public static void main(String[] args) throws SQLException, IOException {
		
		String URl = "http://www.mysmartprice.com/mobile/celkon-a400-plus-msp7575";
		
		Document doc = Jsoup.connect(URl).get();
		Elements spans = new Elements();
		String data =  doc.select("div[class=store_pricetable online_line]").first().attr("data-storename");
		// price of a product
		//<div class="store_pricetable online_line" data-relrank="2279" data-pricerank="2349" data-storename="amazon">  
		//String data = spans1.text();
		data = data.replaceAll("[^0-9.]", "");
		System.out.println(data);
	}
	
	
}