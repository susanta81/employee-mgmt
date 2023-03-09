package com.suspro.employeemgmt.model;

import java.util.List;
public class EmployeeDetails {

	private long employeeId;
	private String firstName;
	private String lastName;
	private String emailId;
	private List<Product> products;

	public EmployeeDetails(long employeeId, String firstName, String lastName, String emailId, List<Product> products) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.products = products;
	}

	public EmployeeDetails() {
		super();
	}



	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}







}
