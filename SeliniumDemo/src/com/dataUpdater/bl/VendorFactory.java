package com.dataUpdater.bl;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class VendorFactory {

	static VendorDataUpdater vdu = null;
	
	private VendorFactory(){
		
	}
	public static VendorDataUpdater getInstance(String vendor,String url,HtmlUnitDriver driver,String mode){
		
		/*if(vendor.equals("amazon")){
			vdu =  new AmazonDataUpdater(url,driver,mode);
		}
		if(vendor.equals("infibeam")){
			vdu =  new InfibeamDataUpdater(url,driver,mode);
		}*/
		return vdu;
	}
	
}
