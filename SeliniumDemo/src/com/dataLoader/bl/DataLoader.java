package com.dataLoader.bl;

import java.util.List;
import java.util.Map;

import com.dataLoader.model.ProductMaster;
import com.dataLoader.model.VendorProductData;

public interface DataLoader {

	
	 
	
	public void processList();
	
	
	public List<ProductMaster> getItemsList();
	
	
	
	/**
	 *  get the items from the database into an arrayList of PoroductDataVO 
	 * @return boolean
	 */
	public boolean populateList();
	public boolean saveResults(Map<ProductMaster,VendorProductData> results);
	

}
