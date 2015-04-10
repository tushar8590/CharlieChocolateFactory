package com.dataLoader.model;

public class ProductMaster implements Comparable {

	
	 private String productId;
	 private String productTitle;
	 private String productBrand;
	 
	 
	 /*private String productPrice;
	 private String productURL;
	 private String productImage;
	 private String productMainCategory;
	 private String productSubCategory;
	 private String productBrand;
	 private String productInStock;
	 private String productSpecId;*/
	 
	 
	 public String getProductId() {
		return productId;
	}



	/**
	 * @param productId
	 * @param productTitle
	 */
	public ProductMaster(String productId, String productTitle,String brand) {
		super();
		this.productId = productId;
		this.productTitle = productTitle;
		this.productBrand = brand;
	}

	public ProductMaster(String productId, String productTitle) {
		super();
		this.productId = productId;
		this.productTitle = productTitle;

	}
	

	public String getProductTitle() {
		return productTitle;
	}




	@Override
	public int compareTo(Object obj1) {
		ProductMaster obj2 = (ProductMaster) obj1;
		return this.productTitle.compareToIgnoreCase(obj2.getProductTitle());
	}



	public String getProductBrand() {
		return productBrand;
	}



	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

}
