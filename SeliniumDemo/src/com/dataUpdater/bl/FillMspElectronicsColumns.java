
package com.dataUpdater.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataLoader.dao.JDBCConnection;
import com.dataLoader.dao.SQLQueries;

public class FillMspElectronicsColumns {

	private static String host = "jdbc:mysql://localhost:3306/aapcompare_test";
	private static String userName = "root";
	private static String password = "";


	private static Connection con;
	static JDBCConnection conn = JDBCConnection.getInstance();;


	private ResultSet rs;

	public static void main(String args[]){
		try{

			// Load the Driver class. 
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver here.

			//Create the connection using the static getConnection method
			con = DriverManager.getConnection (host,userName,password);
			con.setAutoCommit(false);

		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			String query ="SELECT DISTINCT menu_level1, menu_level2, section FROM new_menu ";

			String sectionQuery ="SELECT DISTINCT section FROM msp_product_url WHERE STATUS = 'i'";

			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();


			ResultSet rs;

			rs = stmt.executeQuery(query);
			Map<String,String> columnMappingMap = new HashMap<>();
			while(rs.next())
			{
				try {
					String menu_level1 =rs.getString("menu_level1");

					String menu_level2 =rs.getString("menu_level2");
					String section =rs.getString("section");

					columnMappingMap.put(section, menu_level1+"*"+menu_level2);


				}
				catch(Exception e){
					continue;
				}

			}

			rs = stmt1.executeQuery(sectionQuery);



			while(rs.next())
			{
				String section = rs.getString("section");

				if(columnMappingMap.containsKey(section)){
					String value = columnMappingMap.get(section);
					String[] parts = value.split("\\*");
					//saveData(model,parts[0],parts[1],spec_url,section);
					List<String> params = new ArrayList<>();
					query = SQLQueries.updateMSPElectronicsTableColumns;

					params.add(parts[0]);
					params.add(parts[1]);
					conn.upsertData(query, params);
					System.out.println("updated");
					params.clear();
				}

			}

			System.out.println(columnMappingMap.size());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
