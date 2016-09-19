package com.dataUpdater.bl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";

	public String getFKPrice(String prodctId) throws Exception {

		HttpURLConnectionExample http = new HttpURLConnectionExample();

		System.out.println("Testing 1 - Send Http GET request");
		return http.sendGet(prodctId);
	}

	// HTTP GET request
	private String sendGet(String productId) throws Exception {

		String url = "https://affiliate-api.flipkart.net/affiliate/product/json?id="+productId;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "PHP-ClusterDev-Flipkart/0.1");
		con.addRequestProperty("Fk-Affiliate-Id", "a123pp9aa");
		con.addRequestProperty("Fk-Affiliate-Token", "3523e24e9a5047f7ab2c0dceef27a84c");
		con.setRequestProperty("Cache-Control","no-cache");

		int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		String str = response.toString().split("amount")[1];
		//System.out.println(result);
		System.out.println(str.substring(str.indexOf(":")+1,str.indexOf(",")));
		return str.substring(str.indexOf(":")+1,str.indexOf(","));

	}

	// HTTP POST request


}