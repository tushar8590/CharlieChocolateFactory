package com.dataLoader.model;

import java.util.List;

public class Product {

	private List<Store> stores;
	private String sharelink;
	private String model;
	private String brand;
	private String section;
	private String id;
	public List<Store> getStores() {
		return stores;
	}
	public void setStores(List<Store> stores) {
		this.stores = stores;
	}
	public String getSharelink() {
		return sharelink;
	}
	public void setSharelink(String sharelink) {
		this.sharelink = sharelink;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Override
	public String toString() {
		return "Product [stores=" + stores + ", sharelink=" + sharelink
				+ ", model=" + model + ", brand=" + brand + ", section="
				+ section + ", id=" + id + "]";
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
