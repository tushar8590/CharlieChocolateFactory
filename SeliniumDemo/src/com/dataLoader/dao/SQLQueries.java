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
	public static String insertPCIFeed = "insert into pci_product_feed_temp values (?,?,?,?,?,?,?,?,?,?,?,?)";
	public static String getURLsPCIFeed = "select id,url,website from pci_product_feed where url_mapped = 'F' LIMIT 3000";
	public static String insertproduct_pci_url_temp = "INSERT INTO product_pci_url_temp VALUES(?,?,?)";
	public static String updatePCIFeedForUrlMapping = "update pci_product_feed_temp set url_mapped = 'T' where id = ? and website = ?";
	
	// PCIfeed updater queries
	public static String findProductExist = "select * from pci_product_feed_temp where id = ? and website = ?";
	public static String updatePCIFeed = "update pci_product_feed_temp set price = ? ,offer = ? , stock = ?,url_mapped = 'U' where id = ? and website = ?";
	
	
	
	public static String googleShoesData ="SELECT product_id,product_title FROM product_details_snd_top_selling WHERE product_sub_cat_1 = ? AND google_map='T' AND product_brand IN('Reebok','Bata','Nike','Adidas','Liberty','Woodland','Valentino','Converse','Lancer','Lotto','RedTape','Puma','Paragon','Relaxo','Action','Fila','Coolers','Force 10','Sharon','Italiano','Lues Alberto','Amblin','Khadims','Genius','Cyke','G Sports','Montee Cairo','Globalite','Wood Style','Alberto Torresi','Aria','Catwalk','Sole Threads','Gliders','Timberland','United Colors of Benetton','Tiptopp','Sparx') ORDER BY product_title";

	 public static String updategoogleMaster = "update product_details_snd_top_selling set google_map = 'T' where product_id = ?";
	 
	 
	 public static String getPartiallyResolvedURLS = "SELECT id,url,website FROM pci_product_feed_temp  WHERE url_mapped='F'";
	 
	 public static String getURLFromPCIFeed = "select product_id,url from cat_search_product_data where website = ? and  spec_resolved = 'F' LIMIT 5000";
	 public static String insertPciSpecMaster = "insert into pci_spec_master(product_id,product_spec_details) VALUES(?,?)";
	 public static String updatePCISpecFlag = "update cat_search_product_data set spec_resolved = 'T' where product_id = ?";
	
	 /*adding new queries for the updater   */
	 public static String getMasterFeedDataForupdate = "select * from pci_product_feed_temp where section = ? LIMIT 1";
	 // insert query for the multiVendor product data
	 public static String insertElecMultiVendorData = "insert into elec_multi_vendor(id,model,url,website,offer,price,stock,color,rating) values(?,?,?,?,?,?,?,?,?)";
	
	 public static String  updateElecProductMaster = "update elec_product_master set updated_flag = 'Y',multi_vendor ='Y' where product_id = ?";
	 public static String logElecUnmapped = "insert into elec_multi_vendor_unmapped values(?,?,?,?)";
	 public static String deferUpdateElecMultiVendor = "update elec_product_master set multi_vendor = 'D', updated_flag = 'D' where product_id = ?";
	 
	 
	 
	 // for test
	 public static String getDesc  = "Select  description from catproduct_omg_all where section = 'Root' and  website ='zovi'";
	 public static String getDataUpdaterFromURLTemp  = "SELECT  id,url,vendor FROM product_pci_url_temp where flag = 'F' LIMIT 5000";
	 public static String updateURL = "update pci_product_feed_temp set url = ? where id = ? and website = ?";
	 public static String updateTempURLFlag = "update product_pci_url_temp set flag = 'T' where url = ? and id = ? and vendor = ?";
	 
	 
	 // updated code for the Shoe Mapper using executor framework 
	 // Tushar
	 // 30 Sept 2015
	 public static String getShoesFKRecords  = "SELECT * FROM `men_footwear_fk` WHERE brand LIKE '%PUMA%' AND  mapping_flag = 'N' AND section  IN ('Casual Shoes','Sports Shoes') GROUP BY model LIMIT 1";
	   public static String insertShoeMappingData = "insert into shoe_mapping_data(id,model,url,website,offer,price,stock,color,rating) values(?,?,?,?,?,?,?,?,?)";
	   public static String  updateShoeMappingData = "update men_footwear_fk SET mapping_flag = 'Y' where product_id = ?";

	 // MSP related queries
	   // Tushar 
	   // 1sth October 2015
	   public static String  insertMspProductUrl = "insert into msp_product_url(product_id,url,section,status) values(concat(LAST_INSERT_ID() + 1),?,?,?)";
	   public static String insertElectronicData = "insert into sp_electronics(model,price,url,image_url,section,mapped_flag) values()";
	   public static String getMspUrls  = "SELECT * FROM `msp_product_url` -- where temp_flag = 'N' "; 
	   public static String insertMspProductData = "insert into msp_electronics(product_id,section,model,url,price,image,cod,delivery_time,rating,emi_avaliable,temp_flag) values (?,?,?,?,?,?,?,?,?,?,'X')";
	   public static String updateMSPUrlFlag = "UPDATE msp_product_url SET temp_flag = 'X' where url = ?"; // X stands for Data Found
	   public static String updateSkipForNoData = "UPDATE msp_product_url SET temp_flag = 'N' where url = ?";  // N stands for No data Found
	   
	   // MSP related QUeries for unresolved Urls
	   public static String getUnresolvedUrls = "SELECT id,url,website FROM msp_electronics  WHERE url_mapped='F'";
	   public static String udpateMspUResolvedUrl = "update msp_electronics set resolved_url = ?, url_mapped = 'T' where product_id = ? and url = ? and website = ?";
	   public static String udpateMspUResolvedUrlDeffered = "update msp_electronics set url_mapped = 'N' where product_id = ?"; // n stands for data not found
	   
	   // MSP related queries for spec udpate
	   public static String updateMSPSpec = "update msp_product_url set product_spec = ?,temp_flag = 'X' where spec_url = ?"; // x stands for Data Found
	   public static String updateSkipForNoSpecData = "UPDATE msp_product_url SET temp_flag = 'N' where spec_url = ?"; // N stands for data not found
	   public static String fetchMainCategoryMap= "select * from category_main_url";
	   public static String fetchAllUrl= "select url from msp_product_url";
	   public static String updateMSPProductTableColumns = "update msp_product_url set model = SUBSTRING_INDEX(url,'/',-1),menu_level1 = ?,menu_level2 = ?,spec_url = REPLACE(REPLACE(CONCAT('http://www.mysmartprice.com/product/',SUBSTRING_INDEX(url,'/',-2),'-other#tab_spec'),'msf','mst'),'msp','mst'),temp_flag = 'f' where section = ? and status = 'i' and model is null"; // x stands for Data Found
}
