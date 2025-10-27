package com.snhu.contacts;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ContactServiceTest {

    private ContactService service;

    @Before
    public void setup() {
        service = new ContactService();
    }

    @Test
    public void addAndGetContact() {
        service.addContact("ID1", "Lynne", "Lumanog", "7145551234", "OC");
        Contact c = service.get("ID1");
        assertNotNull(c);
        assertEquals("Lynne", c.getFirstName());
    }

    @Test
    public void cannotAddDuplicateId() {
        service.addContact("DUP", "A", "B", "1112223333", "Addr");
        try { service.addContact("DUP", "C", "D", "4445556666", "Addr2"); fail(); }
        catch (IllegalArgumentException expected) {}
    }

    @Test
    public void deleteContactById() {
        service.addContact("DEL", "A", "B", "1112223333", "Addr");
        assertTrue(service.deleteContact("DEL"));
        assertNull(service.get("DEL"));
        assertFalse(service.deleteContact("DEL"));
    }

    @Test
    public void updateFieldsById() {
        service.addContact("UP", "A", "B", "1112223333", "Addr");
        service.updateFirstName("UP", "Lynne");
        service.updateLastName("UP", "Lumanog");
        service.updatePhone("UP", "7145551234");
        service.updateAddress("UP", "Orange County");
        Contact c = service.get("UP");
        assertEquals("Lynne", c.getFirstName());
        assertEquals("Lumanog", c.getLastName());
        assertEquals("7145551234", c.getPhone());
        assertEquals("Orange County", c.getAddress());
    }

    @Test
    public void updateFailsIfInvalid() {
        try { service.updateFirstName("MISSING", "X"); fail(); } catch (IllegalArgumentException expected) {}
        service.addContact("UP2", "A", "B", "1112223333", "Addr");
        try { service.updatePhone("UP2", "bad"); fail(); } catch (IllegalArgumentException expected) {}
    }
}
