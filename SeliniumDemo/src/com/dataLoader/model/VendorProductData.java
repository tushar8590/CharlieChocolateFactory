/**
 * 
 */
package com.dataLoader.model;

/**
 * @author jn1831
 *
 */
public class VendorProductData {

	 private String productId;
	 private String productTitle;
	 private String productPrice;
	 private String productURL;
	 private String productVendor;
	 private String productReviewId = "";
	 private String productCouponId= "";
	 
	/**
	 * @param productId
	 * @param productTitle
	 * @param productPrice
	 * @param productURL
	 * @param productVendor
	 * @param productBrand
	 * @param productReviewId
	 * @param productCouponId
	 */
	 
	public VendorProductData(String productId, String productTitle,
			String productPrice, String productURL, String productVendor, String productReviewId, String productCouponId) {
		
		this.productId = productId;
		this.productTitle = productTitle;
		this.productPrice = productPrice;
		this.productURL = productURL;
		this.productVendor = productVendor;
	//	this.productBrand = productBrand;
		this.productReviewId = productReviewId;
		this.productCouponId = productCouponId;
	}

	public VendorProductData(String productId, String productURL) {
		super();
		this.productId = productId;
		this.productURL = productURL;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public String getProductURL() {
		return productURL;
	}

	public String getProductVendor() {
		return productVendor;
	}
	public String getProductReviewId() {
		return productReviewId;
	}

	public String getProductCouponId() {
		return productCouponId;
	}
	 
	
}
