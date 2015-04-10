/**
 * 
 */
package com.dataLoader.dao;

/**
 * @author jn1831
 *
 */



import java.util.*;
import java.util.List;

public class SQLQueries {

	//public static String insertProductMaster;
	public static String insertProductVendor = "insert into product_vendor values(?,?,?,?,?,?,?,?,?)";
	public static String rawProductMaster = "SELECT product_id,product_title FROM product_master WHERE product_sub_category = ? and mapped_flag = 'F'";
	public static String updateProductMaster = "update product_master set mapped_flag = 'T' where product_id = ?";
	public static String updateProductMasterFlipkart = "update product_master set mapped_fk = 'T' where product_id = ?";
	public static String updateProductMasterNaaptol = "update product_master set mapped_naaptol = 'T' where product_id = ?";
	//public static String 
	public static String updateProductInfiFLag = "update product_master set mapped_infi = 'T'  where product_id = ?";

	public static String flipkartRawProductMaseter = "SELECT product_id,product_title FROM product_master WHERE product_main_category = ? and mapped_fk = 'F'";
	//flipkart spec loader 
	public static String flipkartProductURL = "SELECT * FROM product_vendor WHERE product_vendor  = ? AND product_id LIKE ? AND product_id NOT IN (select product_id FROM product_specs)";
	public static String flipkartCamsURL = "SELECT * FROM product_vendor WHERE master_product_id IN (SELECT product_id FROM product_master WHERE product_sub_category IN (?)) AND product_vendor = ?";
	public static String insertProductSpecs = "insert into product_specs(product_id,product_spec_details) VALUES(?,?)";
	public static String infibeamRawProductMaseter = "SELECT product_id,product_title FROM product_master WHERE product_sub_category = ? and mapped_infi = 'F'";
	public static String naaptolRawProductMaseter = "SELECT product_id,product_title FROM product_master WHERE product_sub_category = ? and mapped_naaptol = 'F'";
	public static String insertPCIFeed = "insert into pci_product_feed values (?,?,?,?,?,?,?,?,?,?,?)";
	public static String getURLsPCIFeed = "select id,url,website from pci_product_feed where url_mapped = 'F' LIMIT 3000";
	public static String insertproduct_pci_url_temp = "INSERT INTO product_pci_url_temp VALUES(?,?,?)";
	public static String updatePCIFeed = "update pci_product_feed set url_mapped = 'T' where id = ? and website = ?";
	
	
	public static String googleShoesData ="SELECT product_id,product_title FROM product_details_snd_top_selling WHERE product_sub_cat_1 = ? AND google_map='T' AND product_brand IN('Reebok','Bata','Nike','Adidas','Liberty','Woodland','Valentino','Converse','Lancer','Lotto','RedTape','Puma','Paragon','Relaxo','Action','Fila','Coolers','Force 10','Sharon','Italiano','Lues Alberto','Amblin','Khadims','Genius','Cyke','G Sports','Montee Cairo','Globalite','Wood Style','Alberto Torresi','Aria','Catwalk','Sole Threads','Gliders','Timberland','United Colors of Benetton','Tiptopp','Sparx') ORDER BY product_title";

	 public static String updategoogleMaster = "update product_details_snd_top_selling set google_map = 'T' where product_id = ?";
	 
	 
	 public static String getPartiallyResolvedURLS = "SELECT * FROM product_pci_url_temp WHERE url LIKE 'http://dl.flipkart.com%'";
	 
	 public static String getURLFromPCIFeed = "select product_id,url from cat_search_product_data where website = ? and  spec_resolved = 'F' LIMIT 5000";
	 public static String insertPciSpecMaster = "insert into pci_spec_master(product_id,product_spec_details) VALUES(?,?)";
	 public static String updatePCISpecFlag = "update cat_search_product_data set spec_resolved = 'T' where product_id = ?";
	
	 /*adding new queries for the updater   */
	 public static String getMasterFeedDataForupdate = "select * from cat_search_product_data where section = ? and updated_flag = 'N' LIMIT 10";
	 public static String insertElecMultiVendorData = "insert into elec_multi_vendor()";
	 
	 
	 
	 
}
