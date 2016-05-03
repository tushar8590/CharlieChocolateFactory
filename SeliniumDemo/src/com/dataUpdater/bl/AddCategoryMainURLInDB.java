package com.dataUpdater.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddCategoryMainURLInDB {

	static Map<String,List<String>> urlMap;
	private static String host = "jdbc:mysql://localhost:3306/aapcompare_test";
	private static String userName = "root";
	private static String password = "";
	private static Connection con;
	private ResultSet rs;

	public static void main(String args[]){
		
		
		try{

			// Load the Driver class. 
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver here.

			//Create the connection using the static getConnection method
			con = DriverManager.getConnection (host,userName,password);

		}catch(Exception e){
			e.printStackTrace();
		}

		urlMap = new HashMap<String,List<String>>();
		urlMap.put("Mobiles", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/mobile-price-list-in-india.html","http://www.mysmartprice.com/mobile/pricelist/pages/mobile-price-list-in-india-","77"));
		urlMap.put("Tablets", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/tablet-price-list-in-india.html","http://www.mysmartprice.com/mobile/pricelist/pages/tablet-price-list-in-india-","11"));
		urlMap.put("Landline Phones", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/landline-phones-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/landline-phones-price-list-in-india-","7"));
		urlMap.put("Smart Watches", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/smart-watches-price-list-in-india.html","http://www.mysmartprice.com/mobile/pricelist/pages/smart-watches-price-list-in-india-","4"));
		urlMap.put("MicroSD Memory Cards", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/memory-cards-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/memory-cards-price-list-in-india-","6"));
		urlMap.put("Mobile Battery", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/batteries-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/batteries-price-list-in-india-","28"));
		urlMap.put("Mobile Chargers", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/chargers-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/chargers-price-list-in-india-","12"));
		urlMap.put("Cables", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/cables-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/cables-price-list-in-india-","2"));
		urlMap.put("Power Banks", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/power-banks-price-list-in-india.html ","http://www.mysmartprice.com/accessories/pricelist/pages/power-banks-price-list-in-india- ","38"));
		urlMap.put("Smart Bands", Arrays.asList("http://www.mysmartprice.com/mobile/pricelist/fitness-tracker-price-list-in-india.html","http://www.mysmartprice.com/mobile/pricelist/pages/fitness-tracker-price-list-in-india-","2"));
		urlMap.put("Wireless Speakers", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/wireless-speakers-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/wireless-speakers-price-list-in-india-","19"));
		urlMap.put("Headphones", Arrays.asList("http://www.mysmartprice.com/electronics/pricelist/headphones-price-list-in-india.html","http://www.mysmartprice.com/electronics/pricelist/pages/headphones-price-list-in-india-","19"));
		urlMap.put("Bluetooth Headsets", Arrays.asList("http://www.mysmartprice.com/electronics/pricelist/bluetooth-headsets-price-list-in-india.html ","http://www.mysmartprice.com/electronics/pricelist/pages/bluetooth-headsets-price-list-in-india- ","13"));
		urlMap.put("Wired Headsets", Arrays.asList("http://www.mysmartprice.com/electronics/pricelist/headsets-price-list-in-india.html","http://www.mysmartprice.com/electronics/pricelist/pages/headsets-price-list-in-india-","28"));
		urlMap.put("Car Chargers", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/car-chargers-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/car-chargers-price-list-in-india-","11"));
		urlMap.put("Car Kits", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/car-kits-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/car-kits-price-list-in-india-","3"));
		urlMap.put("Car Cradles & Mounts", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/car-cradles-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/car-cradles-price-list-in-india- ","7"));
		urlMap.put("GPS Navigation Devices", Arrays.asList("http://www.mysmartprice.com/accessories/pricelist/gps-navigation-devices-price-list-in-india.html","http://www.mysmartprice.com/accessories/pricelist/pages/gps-navigation-devices-price-list-in-india-","3"));
		urlMap.put("All Laptops", Arrays.asList("http://mysmartprice.com/computer/pricelist/laptops-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/laptops-price-list-in-india-","27"));
		urlMap.put("Pen Drives Price", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-pendrive-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-pendrive-price-list-in-india-","16"));
		urlMap.put("External Hard Disks Price", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-external-hard-disks-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-external-hard-disks-price-list-in-india-","6"));
		urlMap.put("External DVD Writers", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-external-dvd-writer-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-external-dvd-writer-price-list-in-india-","2"));
		urlMap.put("SD Memory Cards", Arrays.asList("http://mysmartprice.com/camera/pricelist/sd-memory-cards-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/sd-memory-cards-price-list-in-india-","5"));
		urlMap.put("MicroSD Memory Cards", Arrays.asList("http://mysmartprice.com/accessories/pricelist/memory-cards-price-list-in-india.html","http://mysmartprice.com/accessories/pricelist/pages/memory-cards-price-list-in-india-","6"));
		urlMap.put("Card Readers", Arrays.asList("http://mysmartprice.com/computer/pricelist/card-reader-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/card-reader-price-list-in-india-","4"));
		urlMap.put("External SSD", Arrays.asList("http://mysmartprice.com/computer/pricelist/external-ssd-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/external-ssd-price-list-in-india-","2"));
		urlMap.put("Network Attached Storage", Arrays.asList("http://mysmartprice.com/computer/pricelist/network-attached-storage-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/network-attached-storage-price-list-in-india- ","2"));
		urlMap.put("Desktop", Arrays.asList("http://mysmartprice.com/computer/pricelist/desktop-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/desktop-price-list-in-india- ","5"));
		urlMap.put("Graphic Cards", Arrays.asList("http://mysmartprice.com/computer/pricelist/graphic-cards-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/graphic-cards-price-list-in-india-","4"));
		urlMap.put("Internal Hard Drives", Arrays.asList("http://mysmartprice.com/computer/pricelist/internal-hard-disk-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/internal-hard-disk-price-list-in-india-","7"));
		urlMap.put("Motherboards", Arrays.asList("http://mysmartprice.com/computer/pricelist/motherboards-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/motherboards-price-list-in-india-","7"));
		urlMap.put("Power Supply Unit", Arrays.asList("http://mysmartprice.com/computer/pricelist/smps-power-supplies-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/smps-power-supplies-price-list-in-india-","3"));
		urlMap.put("Processor Fans & Cooling", Arrays.asList("http://mysmartprice.com/computer/pricelist/processor-fans-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/processor-fans-price-list-in-india- ","3"));
		urlMap.put("Processors", Arrays.asList("http://mysmartprice.com/computer/pricelist/processors-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/processors-price-list-in-india- ","3"));
		urlMap.put("RAM", Arrays.asList("http://mysmartprice.com/computer/pricelist/ram-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/ram-price-list-in-india- ","11"));
		urlMap.put("Sound Cards", Arrays.asList("http://mysmartprice.com/computer/pricelist/sound-cards-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/sound-cards-price-list-in-india-","2"));
		urlMap.put("TV Tuners", Arrays.asList("http://mysmartprice.com/computer/pricelist/tv-tuners-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/tv-tuners-price-list-in-india-","2"));
		urlMap.put("Computer Cabinets", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-cabinets-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-cabinets-price-list-in-india- ","3"));
		urlMap.put("Monitors", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-monitor-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-monitor-price-list-in-india-","6"));
		urlMap.put("Mouse", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-mouse-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-mouse-price-list-in-india- ","22"));
		urlMap.put("Keyboards", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-keyboard-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-keyboard-price-list-in-india- ","13"));
		urlMap.put("Headphones", Arrays.asList("http://mysmartprice.com/electronics/pricelist/headphones-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/headphones-price-list-in-india-","19"));
		urlMap.put("Computer Headsets", Arrays.asList("http://mysmartprice.com/electronics/pricelist/headsets-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/headsets-price-list-in-india-","28"));
		urlMap.put("Speakers", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-speaker-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-speaker-price-list-in-india-","25"));
		urlMap.put("Webcams", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-webcam-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-webcam-price-list-in-india-","3"));
		urlMap.put("Laptop Batteries", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-laptop-battery-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-laptop-battery-price-list-in-india-","3"));
		urlMap.put("Laptop Chargers & Adapters", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-laptop-chargers-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-laptop-chargers-price-list-in-india- ","2"));
		urlMap.put("Cooling Pads", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-cooling-pads-and-stands-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-cooling-pads-and-stands-price-list-in-india-","3"));
		urlMap.put("Touch Pads", Arrays.asList("http://mysmartprice.com/computer/pricelist/touch-pads-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/touch-pads-price-list-in-india- ","2"));
		urlMap.put("Laser Pointers", Arrays.asList("http://mysmartprice.com/computer/pricelist/laser-pointers-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/laser-pointers-price-list-in-india- ","2"));
		urlMap.put("Laptop Docking Stations", Arrays.asList("http://mysmartprice.com/computer/pricelist/laptop-docking-stations-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/laptop-docking-stations-price-list-in-india-","2"));
		urlMap.put("Microphones", Arrays.asList("http://mysmartprice.com/computer/pricelist/microphones-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/microphones-price-list-in-india-","6"));
		urlMap.put("Presentation Remotes", Arrays.asList("http://mysmartprice.com/computer/pricelist/presentation-remotes-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/presentation-remotes-price-list-in-india-","2"));
		urlMap.put("Laptop Screen Protectors", Arrays.asList("http://mysmartprice.com/computer/pricelist/portable-computer-screen-filters-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/portable-computer-screen-filters-price-list-in-india-","2"));
		urlMap.put("WiFi Routers", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-router-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-router-price-list-in-india-","10"));
		urlMap.put("Data Cards", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-data-cards-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-data-cards-price-list-in-india-","2"));
		urlMap.put("Wireless USB Adapters", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-wireless-usb-adapters-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-wireless-usb-adapters-price-list-in-india-","3"));
		urlMap.put("Access Points", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-access-points-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-access-points-price-list-in-india-","2"));
		urlMap.put("Switches", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-switches-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-switches-price-list-in-india- ","5"));
		urlMap.put("Network Interface Cards", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-network-interface-cards-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-network-interface-cards-price-list-in-india-","2"));
		urlMap.put("USB Hubs", Arrays.asList("http://mysmartprice.com/computer/pricelist/usb-hubs-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/usb-hubs-price-list-in-india-","3"));
		urlMap.put("Bluetooth Adapters", Arrays.asList("http://mysmartprice.com/computer/pricelist/bluetooth-adapters-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/bluetooth-adapters-price-list-in-india-","2"));
		urlMap.put("Modems", Arrays.asList("http://mysmartprice.com/computer/pricelist/modems-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/modems-price-list-in-india-","2"));
		urlMap.put("Power LAN Adapters", Arrays.asList("http://mysmartprice.com/computer/pricelist/power-lan-adapters-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/power-lan-adapters-price-list-in-india-","2"));
		urlMap.put("Repeaters & Extenders", Arrays.asList("http://mysmartprice.com/computer/pricelist/repeaters-and-extenders-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/repeaters-and-extenders-price-list-in-india-","2"));
		urlMap.put("Print Servers", Arrays.asList("http://mysmartprice.com/computer/pricelist/print-servers-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/print-servers-price-list-in-india-","2"));
		urlMap.put("Antennas & Amplifiers", Arrays.asList("http://mysmartprice.com/computer/pricelist/antennas-and-amplifiers-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/antennas-and-amplifiers-price-list-in-india-","2"));
		urlMap.put("Single Function Printers", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-printer-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-printer-price-list-in-india-","5"));
		urlMap.put("Multifunction Printers", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-multi-function-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-multi-function-price-list-in-india-","3"));
		urlMap.put("Scanners", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-scanner-price-list-in-india.html ","http://mysmartprice.com/computer/pricelist/pages/computer-scanner-price-list-in-india-","2"));
		urlMap.put("Inkjet Inks", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-inks-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-inks-price-list-in-india-","8"));
		urlMap.put("Laser Toners", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-toners-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-toners-price-list-in-india-","17"));
		urlMap.put("Antivirus and Security Software", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-software-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-software-price-list-in-india- ","9"));
		urlMap.put("Microsoft Office", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-office-tools-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-office-tools-price-list-in-india- ","2"));
		urlMap.put("Microsoft Windows", Arrays.asList("http://mysmartprice.com/computer/pricelist/computer-operating-system-price-list-in-india.html","http://mysmartprice.com/computer/pricelist/pages/computer-operating-system-price-list-in-india-","2"));
		urlMap.put("Digital Cameras", Arrays.asList("http://mysmartprice.com/camera/pricelist/digital-camera-price-list-in-india.html ","http://mysmartprice.com/camera/pricelist/pages/digital-camera-price-list-in-india- ","8"));
		urlMap.put("Digital SLR Cameras", Arrays.asList("http://mysmartprice.com/camera/pricelist/digital-slr-camera-price-list-in-india.html ","http://mysmartprice.com/camera/pricelist/pages/digital-slr-camera-price-list-in-india- ","3"));
		urlMap.put("Camcorders", Arrays.asList("http://mysmartprice.com/camera/pricelist/camcorder-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/camcorder-price-list-in-india-","2"));
		urlMap.put("Digital Photo Frames", Arrays.asList("http://mysmartprice.com/camera/pricelist/digital-photo-frames-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/digital-photo-frames-price-list-in-india-","2"));
		urlMap.put("Binoculars", Arrays.asList("http://mysmartprice.com/camera/pricelist/binoculars-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/binoculars-price-list-in-india-","6"));
		urlMap.put("Surveillance Camers", Arrays.asList("http://mysmartprice.com/camera/pricelist/surveillance-cameras-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/surveillance-cameras-price-list-in-india-","30"));
		urlMap.put("Camera Lenses", Arrays.asList("http://mysmartprice.com/camera/pricelist/lenses-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/lenses-price-list-in-india-","10"));
		urlMap.put("Lens Filters", Arrays.asList("http://mysmartprice.com/camera/pricelist/filters-price-list-in-india.html ","http://mysmartprice.com/camera/pricelist/pages/filters-price-list-in-india- ","3"));
		urlMap.put("Lens Hoods", Arrays.asList("http://mysmartprice.com/camera/pricelist/lens-hoods-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/lens-hoods-price-list-in-india-","4"));
		urlMap.put("Lens Caps", Arrays.asList("http://mysmartprice.com/camera/pricelist/lens-cap-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/lens-cap-price-list-in-india-","2"));
		urlMap.put("Lens Cleaners & Kits", Arrays.asList("http://mysmartprice.com/camera/pricelist/lens-cleaner-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/lens-cleaner-price-list-in-india-","2"));
		urlMap.put("Adapters & Converters", Arrays.asList("http://mysmartprice.com/camera/pricelist/adapters-and-converters-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/adapters-and-converters-price-list-in-india-","2"));
		urlMap.put("Extension Tubes", Arrays.asList("http://mysmartprice.com/camera/pricelist/extension-tubes-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/extension-tubes-price-list-in-india-","2"));
		urlMap.put("SD Memory Cards", Arrays.asList("http://mysmartprice.com/camera/pricelist/sd-memory-cards-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/sd-memory-cards-price-list-in-india-","5"));
		urlMap.put("Camera Flashes", Arrays.asList("http://mysmartprice.com/camera/pricelist/flashes-price-list-in-india.html ","http://mysmartprice.com/camera/pricelist/pages/flashes-price-list-in-india- ","3"));
		urlMap.put("Camera Remote Controls", Arrays.asList("http://mysmartprice.com/camera/pricelist/camera-remote-controls-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/camera-remote-controls-price-list-in-india-","2"));
		urlMap.put("Rechargeable Batteries", Arrays.asList("http://mysmartprice.com/camera/pricelist/rechargeable-batteries-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/rechargeable-batteries-price-list-in-india-","5"));
		urlMap.put("Battery Chargers", Arrays.asList("http://mysmartprice.com/camera/pricelist/battery-chargers-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/battery-chargers-price-list-in-india-","8"));
		urlMap.put("Camera Screen Protectors", Arrays.asList("http://mysmartprice.com/camera/pricelist/camera-screen-protectors-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/camera-screen-protectors-price-list-in-india-","3"));
		urlMap.put("Viewfinder Extenders", Arrays.asList("http://mysmartprice.com/camera/pricelist/viewfinder-extenders-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/viewfinder-extenders-price-list-in-india-","2"));
		urlMap.put("Camera & Camcorder Batteries", Arrays.asList("http://mysmartprice.com/camera/pricelist/camera-and-camcorder-batteries-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/camera-and-camcorder-batteries-price-list-in-india-","10"));
		urlMap.put("Tripods", Arrays.asList("http://mysmartprice.com/camera/pricelist/tripods-price-list-in-india.html ","http://mysmartprice.com/camera/pricelist/pages/tripods-price-list-in-india- ","7"));
		urlMap.put("Monopods", Arrays.asList("http://mysmartprice.com/camera/pricelist/monopods-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/monopods-price-list-in-india-","5"));
		urlMap.put("Tripod Ball Heads", Arrays.asList("http://mysmartprice.com/camera/pricelist/tripod-ball-heads-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/tripod-ball-heads-price-list-in-india-","2"));
		urlMap.put("Camera Mounts & Clamps", Arrays.asList("http://mysmartprice.com/camera/pricelist/camera-mounts-and-clamps-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/camera-mounts-and-clamps-price-list-in-india-","2"));
		urlMap.put("Adapter Rings", Arrays.asList("http://mysmartprice.com/camera/pricelist/adapter-rings-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/adapter-rings-price-list-in-india-","2"));
		urlMap.put("Diffusers & Modifiers", Arrays.asList("http://mysmartprice.com/camera/pricelist/diffusers-and-modifiers-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/diffusers-and-modifiers-price-list-in-india-","2"));
		urlMap.put("Shoe Mounts", Arrays.asList("http://mysmartprice.com/camera/pricelist/shoe-mounts-price-list-in-india.html ","http://mysmartprice.com/camera/pricelist/pages/shoe-mounts-price-list-in-india- ","2"));
		urlMap.put("All LED & LCD TVs", Arrays.asList("http://mysmartprice.com/electronics/pricelist/tv-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/tv-price-list-in-india-","22"));
		urlMap.put("Apple iPod", Arrays.asList("http://mysmartprice.com/electronics/pricelist/apple-ipod-mp3-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/apple-ipod-mp3-price-list-in-india-","3"));
		urlMap.put("MP3 Players", Arrays.asList("http://mysmartprice.com/electronics/pricelist/ipod-mp3-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/ipod-mp3-price-list-in-india- ","2"));
		urlMap.put("Home Theaters", Arrays.asList("http://mysmartprice.com/electronics/pricelist/home-theaters-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/home-theaters-price-list-in-india-","5"));
		urlMap.put("Video & DVD Players", Arrays.asList("http://mysmartprice.com/electronics/pricelist/video-players-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/video-players-price-list-in-india-","2"));
		urlMap.put("Projectors", Arrays.asList("http://mysmartprice.com/electronics/pricelist/projectors-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/projectors-price-list-in-india-","6"));
		urlMap.put("Smart TV Boxes", Arrays.asList("http://mysmartprice.com/electronics/pricelist/smart-tv-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/smart-tv-price-list-in-india- ","3"));
		urlMap.put("Voice Recorders", Arrays.asList("http://mysmartprice.com/electronics/pricelist/voice-recorders-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/voice-recorders-price-list-in-india- ","2"));
		urlMap.put("AV-Receivers", Arrays.asList("http://mysmartprice.com/electronics/pricelist/av-receivers-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/av-receivers-price-list-in-india-","2"));
		urlMap.put("Boom Boxes", Arrays.asList("http://mysmartprice.com/electronics/pricelist/boom-boxes-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/boom-boxes-price-list-in-india-","2"));
		urlMap.put("FM-Radio", Arrays.asList("http://mysmartprice.com/electronics/pricelist/fm-radio-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/fm-radio-price-list-in-india- ","2"));
		urlMap.put("Karaoke Players", Arrays.asList("http://mysmartprice.com/electronics/pricelist/karaoke-players-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/karaoke-players-price-list-in-india- ","2"));
		urlMap.put("Sound Amplifiers", Arrays.asList("http://mysmartprice.com/electronics/pricelist/sound-amplifiers-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/sound-amplifiers-price-list-in-india-","2"));
		urlMap.put("Gaming Consoles", Arrays.asList("http://mysmartprice.com/electronics/pricelist/gaming-console-price-list-in-india.html","http://mysmartprice.com/electronics/pricelist/pages/gaming-console-price-list-in-india-","3"));
		urlMap.put("Headphones", Arrays.asList("http://mysmartprice.com/electronics/pricelist/headphones-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/headphones-price-list-in-india- ","2"));
		urlMap.put("Bluetooth Headsets", Arrays.asList("http://mysmartprice.com/electronics/pricelist/bluetooth-headsets-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/bluetooth-headsets-price-list-in-india- ","13"));
		urlMap.put("MicroSD Memory Cards", Arrays.asList("http://mysmartprice.com/accessories/pricelist/memory-cards-price-list-in-india.html","http://mysmartprice.com/accessories/pricelist/pages/memory-cards-price-list-in-india-","6"));
		urlMap.put("SD Memory Cards", Arrays.asList("http://mysmartprice.com/camera/pricelist/sd-memory-cards-price-list-in-india.html","http://mysmartprice.com/camera/pricelist/pages/sd-memory-cards-price-list-in-india-","5"));
		urlMap.put("Remote Controls", Arrays.asList("http://mysmartprice.com/electronics/pricelist/remote-controls-price-list-in-india.html ","http://mysmartprice.com/electronics/pricelist/pages/remote-controls-price-list-in-india- ","6"));
		urlMap.put("Mixer Grinder Juicers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/mixers-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/mixers-price-list-in-india-","23"));
		urlMap.put("Electric Kettles", Arrays.asList("http://mysmartprice.com/appliance/pricelist/electric-kettles-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/electric-kettles-price-list-in-india-","10"));
		urlMap.put("Induction Cook Tops", Arrays.asList("http://mysmartprice.com/appliance/pricelist/induction-cooktops-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/induction-cooktops-price-list-in-india- ","10"));
		urlMap.put("Choppers & Blenders", Arrays.asList("http://mysmartprice.com/appliance/pricelist/choppers-blenders-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/choppers-blenders-price-list-in-india-","12"));
		urlMap.put("Electric (Rice) Cookers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/electric-cookers-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/electric-cookers-price-list-in-india-","7"));
		urlMap.put("Pop Up Toasters", Arrays.asList("http://mysmartprice.com/appliance/pricelist/pop-up-toasters-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/pop-up-toasters-price-list-in-india- ","5"));
		urlMap.put("Coffee Makers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/coffee-makers-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/coffee-makers-price-list-in-india-","4"));
		urlMap.put("Microwave Ovens", Arrays.asList("http://mysmartprice.com/appliance/pricelist/microwave-oven-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/microwave-oven-price-list-in-india-","6"));
		urlMap.put("Oven Toaster Grill (OTG)", Arrays.asList("http://mysmartprice.com/appliance/pricelist/otg-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/otg-price-list-in-india-","2"));
		urlMap.put("Sandwich Makers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/sandwich-makers-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/sandwich-makers-price-list-in-india- ","8"));
		urlMap.put("Water Purifiers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/water-purifiers-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/water-purifiers-price-list-in-india- ","10"));
		urlMap.put("Food Processors", Arrays.asList("http://mysmartprice.com/appliance/pricelist/food-processors-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/food-processors-price-list-in-india- ","3"));
		urlMap.put("Air Fryers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/air-fryers-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/air-fryers-price-list-in-india-","2"));
		urlMap.put("Irons", Arrays.asList("http://mysmartprice.com/appliance/pricelist/irons-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/irons-price-list-in-india-","17"));
		urlMap.put("Vacuum Cleaners", Arrays.asList("http://mysmartprice.com/appliance/pricelist/vacuum-cleaners-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/vacuum-cleaners-price-list-in-india- ","6"));
		urlMap.put("Washing Machines", Arrays.asList("http://mysmartprice.com/appliance/pricelist/washing-machines-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/washing-machines-price-list-in-india-","12"));
		urlMap.put("Refrigerators", Arrays.asList("http://mysmartprice.com/appliance/pricelist/refrigerators-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/refrigerators-price-list-in-india-","13"));
		urlMap.put("Emergency Lights", Arrays.asList("http://mysmartprice.com/appliance/pricelist/emergency-light-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/emergency-light-price-list-in-india- ","13"));
		urlMap.put("Air Conditioners", Arrays.asList("http://mysmartprice.com/appliance/pricelist/air-conditioner-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/air-conditioner-price-list-in-india- ","27"));
		urlMap.put("Voltage Stabilizers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/voltage-stabilizers-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/voltage-stabilizers-price-list-in-india-","6"));
		urlMap.put("Inverters & Batteries", Arrays.asList("http://mysmartprice.com/appliance/pricelist/inverters-batteries-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/inverters-batteries-price-list-in-india-","4"));
		urlMap.put("Dishwashers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/dishwashers-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/dishwashers-price-list-in-india- ","2"));
		urlMap.put("Spike Surge Protectors", Arrays.asList("http://mysmartprice.com/appliance/pricelist/spike-surge-protectors-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/spike-surge-protectors-price-list-in-india- ","8"));
		urlMap.put("UPS", Arrays.asList("http://mysmartprice.com/appliance/pricelist/power-supplies-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/power-supplies-price-list-in-india-","2"));
		urlMap.put("Fans", Arrays.asList("http://mysmartprice.com/appliance/pricelist/fans-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/fans-price-list-in-india- ","28"));
		urlMap.put("Geysers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/geysers-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/geysers-price-list-in-india-","10"));
		urlMap.put("Immersion Rods", Arrays.asList("http://mysmartprice.com/appliance/pricelist/immersion-rods-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/immersion-rods-price-list-in-india-","3"));
		urlMap.put("Room Heaters", Arrays.asList("http://mysmartprice.com/appliance/pricelist/room-heaters-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/room-heaters-price-list-in-india-","4"));
		urlMap.put("Air Coolers", Arrays.asList("http://mysmartprice.com/appliance/pricelist/air-coolers-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/air-coolers-price-list-in-india- ","6"));
		urlMap.put("LED Lights", Arrays.asList("http://mysmartprice.com/appliance/pricelist/led-lights-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/led-lights-price-list-in-india-","5"));
		urlMap.put("Power Tools", Arrays.asList("http://mysmartprice.com/appliance/pricelist/power-tools-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/power-tools-price-list-in-india- ","7"));
		urlMap.put("Hand Tools", Arrays.asList("http://mysmartprice.com/appliance/pricelist/hand-tools-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/hand-tools-price-list-in-india-","3"));
		urlMap.put("Measuring Tools", Arrays.asList("http://mysmartprice.com/appliance/pricelist/measuring-tools-price-list-in-india.html ","http://mysmartprice.com/appliance/pricelist/pages/measuring-tools-price-list-in-india- ","2"));
		urlMap.put("Tool Accessories", Arrays.asList("http://mysmartprice.com/appliance/pricelist/tool-accessories-price-list-in-india.html","http://mysmartprice.com/appliance/pricelist/pages/tool-accessories-price-list-in-india-","2"));

		try {
			Iterator it = urlMap.entrySet().iterator();
			Statement stmt = con.createStatement();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				List l1 = (List) pair.getValue();
				String url1 = l1.get(0).toString();
				String url2 = l1.get(1).toString();
				String cat = pair.getKey().toString();
				System.out.println(pair.getKey() + " = " + l1.get(0)+" = "+l1.get(1));


				String query = "INSERT INTO category_main_url (category,first_page_url,second_page_url) " +
						"VALUES ('"+cat+"','"+ url1+"','"+ url2+"')";
				System.out.println(query);
				stmt = con.createStatement();

				stmt.executeUpdate(query);
			}
		}catch(Exception e){
			e.printStackTrace();}
	}
}
