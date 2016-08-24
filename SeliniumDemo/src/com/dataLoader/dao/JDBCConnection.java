package com.dataLoader.dao;

import java.sql.*;
import java.util.List;


public class JDBCConnection {

	
	private String host = "jdbc:mysql://localhost:3306/aapcorjr_dbaapcompare9";
//	private String host = "jdbc:mysql://209.99.16.94:3306/aapcow9a_dbaapcompare9";
//	private String userName = "aapcow9a_adbuser";
	private String userName =	"root";
//	private String password = "Admin@1234$";
	private String password = "";
	private static Connection con;
	
	private static JDBCConnection conn = null;
	private ResultSet rs;
	
	public JDBCConnection() {
		try{
			
			// Load the Driver class. 
		Class.forName("com.mysql.jdbc.Driver");
		// If you are using any other database then load the right driver here.

		//Create the connection using the static getConnection method
		con = DriverManager.getConnection (host,userName,password);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

	public static JDBCConnection getInstance(){
		 
		try {
			if(conn == null || con.isClosed()){
				synchronized(JDBCConnection.class){
					if(conn == null || con.isClosed()){
						conn = new JDBCConnection();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
		
		//return new JDBCConnection();
	}
	
	public ResultSet executeQuery(String query,List<String> params){
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
		if(params!=null){
			int i = 1;
			for(String str: params){
			
				pstmt.setString(i++, str);
				
			}
			}
		
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet executeQuery(String query){
		try {
			Statement stmt = con.createStatement();
		
			rs = stmt.executeQuery(query);
			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean insertData(String query,List<String> params,boolean isUpdate,String parentProductId,String vendor){
		PreparedStatement pstmt  = null;
		PreparedStatement pstmt1 = null;
		
		boolean flag = false;
		
		try {
			pstmt = con.prepareStatement(query);
		
		
		
		if(params!=null){
			int i = 1;
			for(String str: params){
			
				pstmt.setString(i++, str);
				
			}
		}
		
			//System.out.println(query);
			if(pstmt.executeUpdate()>0){
				flag = true;
			}
			
			// this boolean condition is for the data like clothing, home 
			//and furnishing for which compare feature is not req
			
			if(isUpdate){
				
				
			if(vendor.equals("flipkart"))
				pstmt1 = con.prepareStatement(SQLQueries.updateProductMasterFlipkart);
			else if(vendor.equals("infi"))
				pstmt1 = con.prepareStatement(SQLQueries.updateProductInfiFLag);
			else if(vendor.equals("google"))
			    pstmt1 = con.prepareStatement(SQLQueries.updategoogleMaster);
			else
		    	pstmt1 = con.prepareStatement(SQLQueries.updateProductMaster);
			
			
			pstmt1.setString(1,parentProductId);
				if(pstmt1.executeUpdate()>0){
				flag = true;
				}
			}
			
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}finally{
			/*try {
				//pstmt.close();
				//pstmt1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}
		return flag;
	}
	
	
	public boolean upsertData(String query,List<String> params){
		PreparedStatement pstmt  = null;
		boolean flag = false;
		
		try {
		conn = getInstance();
			pstmt = con.prepareStatement(query);
			if(params!=null){
			int i = 1;
			for(String str: params){
				pstmt.setString(i++, str);
			}
		}
		
//			/System.out.println(query);
			if(pstmt.executeUpdate()>0){
				flag = true;
			}
			
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}finally{
			/*try {
				//pstmt.close();
				//pstmt1.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}
		return flag;
	}
	
	
	public void closeConnection(){
		try {
			if(this.con!=null)
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
