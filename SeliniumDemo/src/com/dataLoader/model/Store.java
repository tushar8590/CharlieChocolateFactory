package com.dataLoader.model;

import java.util.ArrayList;
import java.util.List;

public class Store {

	private String  website;
	private String  offer;
	private String  url;
	private String  price;
	private String  stock;
	private String  image;
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Store [website=" + website + ", offer=" + offer + ", url="
				+ url + ", price=" + price + ", stock=" + stock + ", image="
				+ image + "]";
	}
	
	public List<String> getItems(){
		List<String> ls = new ArrayList<String>();
		ls.add(this.getWebsite());
		ls.add(this.getOffer());
		ls.add(this.getUrl());
		ls.add(this.getPrice());
		ls.add(this.getStock());
		ls.add(this.getImage());
		return ls;
	}
	public List<String> getItemsForUpdates(){
		List<String> ls = new ArrayList<String>();
		ls.add(this.getPrice());
		ls.add(this.getOffer());
		ls.add(this.getStock());
		return ls;
	}


}
