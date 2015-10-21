package com.dataUpdater.bl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dataLoader.dao.JDBCConnection;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class Downloader {
//	private static String host = "jdbc:mysql://103.21.58.156:3306/aapcorjr_dbaapcompare9";
	//private static String userName = "aapcorjr_adbuser";
	//private static String password = "adbuseraccess1@34";

	private static String host = "jdbc:mysql://localhost:3306/aapcorjr_dbaapcompare9";
	private static String userName = "root";
	private static String password = "";


	private static Connection con;



	private ResultSet rs;
	public static void main(String[] args) throws Exception {
		List imgUrl = new ArrayList();

		try{

			// Load the Driver class. 
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver here.

			//Create the connection using the static getConnection method
			con = (Connection) DriverManager.getConnection (host,userName,password);
			con.setAutoCommit(false);

		}catch(Exception e){
			e.printStackTrace();
		}


		try {
			String query ="SELECT distinct image FROM msp_electronics";

			Statement stmt = (Statement) con.createStatement();

			ResultSet rs;

			rs = stmt.executeQuery(query);

			while(rs.next())
			{
				String img=rs.getString(1);

				imgUrl.add(img);


			}  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		

		
	for(int i = 0; i < imgUrl.size();i++)
		{
			
		String image = (String) imgUrl.get(i);
			
			downloadFile(image, "C:/Users/jn1831/Desktop/app_product_images/",null);
		}
	}

	public static void downloadFile(String fileURL, String saveDir, String cat)
			throws IOException {
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10,
							disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
						fileURL.length());
			}

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}
}
