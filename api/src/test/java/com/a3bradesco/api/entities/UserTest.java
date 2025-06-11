package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User(1L, "Caue Urbini", "12345678900", "caue@email.com", "Galinha7");

        assertEquals(1L, user.getId());
        assertEquals("Caue Urbini", user.getName());
        assertEquals("12345678900", user.getCpf());
        assertEquals("joao@email.com", user.getEmail());
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
    User u1 = new User(1L, "Caue Urbini", "09876543210", "Caue@email.com", "Galinha7");
    User u2 = new User(1L, "Julia Maria", "09871843210", "Julia@email.com", "Galinha2");
    User u3 = new User(2L, "Caue Urbini", "09876543210", "Caue@email.com", "Galinha7");

    assertEquals(u1, u2); // mesmo ID
    assertNotEquals(u3, u2); // IDs diferentes
    assertEquals(u1.hashCode(), u2.hashCode());
    assertNotEquals(u1.hashCode(), u3.hashCode());
}
}