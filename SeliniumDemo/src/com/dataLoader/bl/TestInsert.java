package com.dataLoader.bl;

import java.util.ArrayList;
import java.util.List;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;

public class TestInsert {

	public TestInsert() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		JDBCConnection conn = JDBCConnection.getInstance();
		String url = "www.google.com";
		
		System.out.println(url);
		
		// save it
		String sql = SQLQueries.insertproduct_pci_url_temp;
		List<String> params = new ArrayList<String>();
		params.add("123");
		params.add("google");
		params.add(url);
		if(conn.upsertData(sql, params)){
			System.out.println("Inserted " +url);
			
		}
	}

}
