package com.suspro.employeemgmt.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Product {

    private long productId;
    private String productName;
    private String productType;
    private String productPrice;
    private long employeeId;
    
    public Product() {
    }

	public Product( long productId, String productName, String productType, String productPrice, long employeeId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.productPrice = productPrice;
		this.employeeId = employeeId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	

	public String getProductName() {
		return productName;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

}
