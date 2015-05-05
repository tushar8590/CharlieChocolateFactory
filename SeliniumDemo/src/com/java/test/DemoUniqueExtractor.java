package com.java.test;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;

public class DemoUniqueExtractor {

	static Set<String> set = null;
	public static void main(String ar[]) throws Exception{
		// get yje query toi fetch the desc
		JDBCConnection conn = JDBCConnection.getInstance();
		String sql = SQLQueries.getDesc;
		ResultSet rs = conn.executeQuery(sql, null);
		set = new HashSet<String>();
		while(rs.next()){
			
			String arr[] = rs.getString(1).split(" ");
			for(String s:arr)
				set.add(s);
		}
		System.out.println(set.size());
		for(String s:set){
			System.out.println(s);
		}
		
	}
}
