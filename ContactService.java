package com.snhu.contacts;

import java.util.*;

public class ContactService {
	private final Map<String, Contact> contacts = new HashMap<>();
	
	public Contact addContact(String contactId, String firstName, String lastName, String phone, String address) {
		if (contacts.containsKey(contactId)) {
			throw new IllegalArgumentException("Contact with this ID already exists");
		}
		Contact c = new Contact(contactId, firstName, lastName, phone, address);
		contacts.put(contactId,  c);
		return c;
	}
	
	public void addContact(Contact contact) {
		Objects.requireNonNull(contact, "contact must not be null");
		String id = contact.getContactId();
		if (contacts.containsKey(id)) throw new IllegalArgumentException("Contact with this ID already exists");
		contacts.put(id, contact);
	}
	
	public boolean deleteContact(String contactId) {
		return contacts.remove(contactId) != null;
	}
	
	public void updateFirstName(String contactId, String newFirstName) {
		getRequired(contactId).setFirstName(newFirstName);
	}
	
	public void updateLastName(String contactId, String newLastName) {
		getRequired(contactId).setLastName(newLastName);
	}
	
	public void updatePhone(String contactId, String newPhone) {
		getRequired(contactId).setAddress(newPhone);
	}
	
	public void updateAddress(String contactId, String newAddress) {
		getRequired(contactId).setAddress(newAddress);
	}
	
	public Contact get(String contactId) {
		return contacts.get(contactId);
	}
	
	public Map<String, Contact> all() {
		return Collections.unmodifiableMap(contacts);
		}
		
	private Contact getRequired(String contactId) {
		Contact c = contacts.get(contactId);
		if (c == null) throw new IllegalArgumentException("No contact found with ID: " + contactId);
		return c;
	}
	
}
