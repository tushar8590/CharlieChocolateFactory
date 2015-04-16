package com.dataUpdater.bl;

public interface VendorDataUpdater  {

	public void processData();
	public boolean insertData();
	public boolean updateData();
	public boolean logError(String vendor,String url,String errorMsg);
	
}
