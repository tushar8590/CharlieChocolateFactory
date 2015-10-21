package com.dataLoader.bl;

import java.sql.ResultSet;
import java.util.Arrays;

import com.dataLoader.dao.JDBCConnection;

public class TempFlagUpdater {
    
    public static void main(String[] args) throws Exception{
      
        
       String sql = "SELECT t1.url FROM msp_product_url t1 INNER JOIN msp_electronics t2 ON t2.model = SUBSTRING_INDEX(t1.url,'/',-1) ";
      JDBCConnection conn = JDBCConnection.getInstance();
      ResultSet rs = conn.executeQuery(sql, null);
      String quer1;
      System.out.println("Starting update");
      while(rs.next()){
          quer1 = "update  msp_product_url set temp_flag = 'Y' where url  = ?";
          conn.upsertData(quer1, Arrays.asList(rs.getString("url")));
      }
      conn.closeConnection();
    }
    
}
