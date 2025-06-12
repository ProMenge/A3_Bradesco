package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User();
        user.setId(1L);
        user.setName("Caue Urbini");
        user.setCpf("12345678900");
        user.setEmail("caue@email.com");
        user.setPassword("Galinha7");

        assertEquals(1L, user.getId());
        assertEquals("Caue Urbini", user.getName());
        assertEquals("12345678900", user.getCpf());
        assertEquals("caue@email.com", user.getEmail());
        assertEquals("Galinha7", user.getPassword());
    }

    @Test
    void testSetters() {
        User user = new User();

        user.setId(2L);
        user.setName("Maria Julia");
        user.setCpf("09876543210");
        user.setEmail("caue@email.com");
        user.setPassword("Galinha7");

        assertEquals(2L, user.getId());
        assertEquals("Maria Julia", user.getName());
        assertEquals("09876543210", user.getCpf());
        assertEquals("caue@email.com", user.getEmail());
        assertEquals("Galinha7", user.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        User u1 = new User();
        u1.setId(1L);
        User u2 = new User();
        u2.setId(1L);
        User u3 = new User();
        u3.setId(2L);
        User u4 = new User();
        u4.setId(null);
        User u5 = new User();
        u5.setId(null);

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());

        assertNotEquals(u1, u3);
        assertNotEquals(u1, u4);

        assertEquals(u4, u5);
        assertEquals(u4.hashCode(), u5.hashCode());

        assertEquals(u1, u1);
        assertNotEquals(u1, null);
        assertNotEquals(u1, "string");
    }

}