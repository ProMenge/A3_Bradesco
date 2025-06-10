package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User(1L, "Caue Urbini", "12345678900", "joao@email.com", "Galinha7");

        assertEquals(1L, user.getId());
        assertEquals("João", user.getName());
        assertEquals("12345678900", user.getCpf());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals("Galinha7", user.getPassword());
    }

    @Test
    void testSetters() {
        User user = new User();

        user.setId(2L);
        user.setName("Maria");
        user.setCpf("09876543210");
        user.setEmail("maria@email.com");
        user.setPassword("senha456");

        assertEquals(2L, user.getId());
        assertEquals("Maria", user.getName());
        assertEquals("09876543210", user.getCpf());
        assertEquals("maria@email.com", user.getEmail());
        assertEquals("senha456", user.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        User u1 = new User(1L, "Ana", "123", "ana@email.com", "abc");
        User u2 = new User(1L, "Outra", "456", "outra@email.com", "def");
        User u3 = new User(2L, "Ana", "123", "ana@email.com", "abc");

        assertEquals(u1, u2);
        assertNotEquals(u1, u3);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertNotEquals(u1.hashCode(), u3.hashCode());
    }
}
