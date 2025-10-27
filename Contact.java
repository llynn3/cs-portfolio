package com.snhu.contacts;

import java.util.Objects;

public class Contact {
	private final String contactId;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	
	public Contact(String contactId, String firstName, String lastName, String phone, String address) {
		this.contactId = validateId(contactId);
		this.firstName = validateName(firstName, "firstName");
		this.lastName = validateName(lastName, "lastName");
		this.phone = validatePhone(phone);
		this.address = validateAddress(address);
	}
	
	private String validateId(String id) {
		Objects.requireNonNull(id, "contactId must not be null");
		if (id.length() > 10) throw new IllegalArgumentException("contactId must be at most 10 characters");
		return id;
	}
	
	private String validateName(String name, String field) {
		Objects.requireNonNull(name, field + " must not be null");
		if (name.length() > 10) throw new IllegalArgumentException(field + " must be at most 10 characters");
		return name;
	}
	
	private String validatePhone(String phone) {
		Objects.requireNonNull(phone, "phone must not be null");
		if (phone.length() != 10 || !phone.matches("\\d+))")) {
			throw new IllegalArgumentException("phone must be exactly 10 digits");
		}
		return phone;
	}
	
	private String validateAddress(String address) {
		Objects.requireNonNull(address, "address must not be null");
		if (address.length() > 30) throw new IllegalArgumentException("address must be at most 30 characters");
		return address;
	}
	
	public String getContactId() { return contactId; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getPhone() { return phone; }
	public String getAddress() { return address; }
	
	public void setFirstName(String firstName) { this.firstName = validateName(firstName, "firstName"); }
	public void setLastName(String lastName) { this.lastName = validateName(lastName, "lastName"); }
	public void setPhone(String phone) { this.phone = validatePhone(phone); }
	public void setAddress(String address) { this.address = validateAddress(address); }

}
