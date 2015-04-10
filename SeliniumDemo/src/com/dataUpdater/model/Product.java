package com.dataUpdater.model;

public class Product implements Comparable<Product> {

	private String productId;
	private String productModel;
	private String productURL;
	private String productWebsite;
	private String productOffer;
	private String productPrice;
	private String productStock;
	private String productColor;
	private String productRating;
	
	// this field is used so that populateList() of CategoryFeedUpdate can add the data
	private String productSearchString;

	

	public Product() {
		
	}

	

	public Product(String productId, String productSearchString) {
		super();
		this.productId = productId;
		this.productSearchString = productSearchString;

	}

	

	@Override
	public int compareTo(Product obj1) {
		return this.productModel.compareToIgnoreCase(obj1.getProductModel());
	}



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public String getProductModel() {
		return productModel;
	}



	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}



	public String getProductURL() {
		return productURL;
	}



	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}



	public String getProductWebsite() {
		return productWebsite;
	}



	public void setProductWebsite(String productWebsite) {
		this.productWebsite = productWebsite;
	}



	public String getProductOffer() {
		return productOffer;
	}



	public void setProductOffer(String productOffer) {
		this.productOffer = productOffer;
	}



	public String getProductPrice() {
		return productPrice;
	}



	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}



	public String getProductStock() {
		return productStock;
	}



	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}



	public String getProductColor() {
		return productColor;
	}



	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}



	public String getProductRating() {
		return productRating;
	}



	public void setProductRating(String productRating) {
		this.productRating = productRating;
	}



	public String getProductSearchString() {
		return productSearchString;
	}



	public void setProductSearchString(String productSearchString) {
		this.productSearchString = productSearchString;
	}

}
