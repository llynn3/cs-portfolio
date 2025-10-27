package com.snhu.contacts;

import static org.junit.Assert.*;
import org.junit.Test;

public class ContactTest {

    @Test
    public void createsValidContact() {
        Contact c = new Contact("ABC123", "Lynne", "Lumanog", "1234567890", "123 Harbor Blvd, OC");
        assertEquals("ABC123", c.getContactId());
        assertEquals("Lynne", c.getFirstName());
        assertEquals("Lumanog", c.getLastName());
        assertEquals("1234567890", c.getPhone());
        assertEquals("123 Harbor Blvd, OC", c.getAddress());
    }

    @Test
    public void idIsImmutable() {
        Contact c = new Contact("ID10", "A", "B", "1112223333", "Addr");
        assertEquals("ID10", c.getContactId());
    }

    @Test
    public void rejectsInvalidFields() {
        try { new Contact(null, "A", "B", "1112223333", "Addr"); fail(); } catch (NullPointerException expected) {}
        try { new Contact("01234567890", "A", "B", "1112223333", "Addr"); fail(); } catch (IllegalArgumentException expected) {}
        try { new Contact("ID", "ABCDEFGHIJK", "B", "1112223333", "Addr"); fail(); } catch (IllegalArgumentException expected) {}
        try { new Contact("ID", "A", "ABCDEFGHIJK", "1112223333", "Addr"); fail(); } catch (IllegalArgumentException expected) {}
        try { new Contact("ID", "A", "B", "123", "Addr"); fail(); } catch (IllegalArgumentException expected) {}
        try { new Contact("ID", "A", "B", "123456789a", "Addr"); fail(); } catch (IllegalArgumentException expected) {}
        try { new Contact("ID", "A", "B", "1112223333", "0123456789012345678901234567891"); fail(); } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void settersWorkAndValidate() {
        Contact c = new Contact("ID", "A", "B", "1112223333", "Addr");
        c.setFirstName("Lynne");
        c.setLastName("Lumanog");
        c.setPhone("7145551234");
        c.setAddress("Short address");
        assertEquals("Lynne", c.getFirstName());
        assertEquals("Lumanog", c.getLastName());
        assertEquals("7145551234", c.getPhone());
        assertEquals("Short address", c.getAddress());
    }
}
