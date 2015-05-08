package com.java.test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;

public class DataMover {

	public static void main(String[] args) throws Exception {
		long startTime = System.nanoTime();
		JDBCConnection conn = JDBCConnection.getInstance();
		String sql = SQLQueries.getDataUpdaterFromURLTemp;
		ResultSet rs = conn.executeQuery(sql, null);
		List<String> params = new ArrayList<String>();
		while(rs.next()){
			String query = SQLQueries.updateURL;
			params.add(rs.getString("url"));
			params.add(rs.getString("id"));
			params.add(rs.getString("vendor"));
			System.out.println(rs.getString("id"));
			if(conn.upsertData(query, params)){
				query = SQLQueries.updateTempURLFlag;
				if(conn.upsertData(query, params)){
					params.clear();
				}
				
			}
			
		}
		long time = System.nanoTime() - startTime;
		System.out.println("Time req for exec "+time/1000000000+"seconds");
	}

}
