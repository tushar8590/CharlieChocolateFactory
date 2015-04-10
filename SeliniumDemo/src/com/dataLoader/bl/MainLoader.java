package com.dataLoader.bl;

import java.util.logging.Level;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.dataUpdater.bl.AmazonDataUpdater;
import com.dataUpdater.bl.InfibeamDataUpdater;
import com.dataUpdater.bl.TestDataUpdater;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class MainLoader {

	public MainLoader() {
	
	}
	static{
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
	}
	public static void main(String[] args) {
	
		
	//DataLoader adl = new AmazonDataLoader();
	//	DataLoader adl = new FlipkartMobileDataLoader();
		//DataLoader adl = new FlipkartMobilesSpecCLoader();
		//DataLoader adl = new InfibeamDataLoader();
		/*GoogleDataLoader  adl = new GoogleDataLoader();
	adl.populateList("sth");
	adl.processList("sth");*/
	
	// testt=ing AmazonDataUpdater
	HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
	  driver.setJavascriptEnabled(true);
	  TestDataUpdater obj = new TestDataUpdater("http://www.homeshop18.com/nokia-c2-03-touch-type-mobile-phone/mobiles/mobile-phones/product:28158273/cid:3027/",driver,"update","homeshop18");
	obj.processData();
	
	}

}
