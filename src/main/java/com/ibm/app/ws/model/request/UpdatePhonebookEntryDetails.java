package com.ibm.app.ws.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.ibm.app.ws.model.response.Address;

public class UpdatePhonebookEntryDetails {

	@NotEmpty(message = "SurName cannot be null")
	@Pattern(regexp = "[a-zA-Z]{3,30}", message = "Enter valid Sur Name")
	private String surName;
	@NotEmpty(message = "FirstName cannot be null")
	@Pattern(regexp = "[a-zA-Z ]{3,30}", message = "Enter valid First Name")
	private String firstName;
	@NotEmpty(message = "Phone Number cannot be Empty")
	@Pattern(regexp = "^[7-9]{1}[0-9]{9}$", message = "Enter valid phone number")
	private String phoneNumber;
	private Address address;

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
