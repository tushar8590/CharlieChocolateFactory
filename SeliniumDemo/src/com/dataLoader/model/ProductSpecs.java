package com.dataLoader.model;

public class ProductSpecs {

	
	 private String productId;
	 private String productSpecsDetails;
	public String getProductId() {
		return productId;
	}
	public String getProductSpecsDetails() {
		return productSpecsDetails;
	}
	public ProductSpecs(String productId, String productSpecsDetails) {
		super();
		this.productId = productId;
		this.productSpecsDetails = productSpecsDetails;
	}
}
