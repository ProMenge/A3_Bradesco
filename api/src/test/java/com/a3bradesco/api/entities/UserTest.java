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
        u1.setName("Caue Urbini");
        u1.setCpf("09876543210");
        u1.setEmail("Caue@email.com");
        u1.setPassword("Galinha7");

        User u2 = new User();
        u2.setId(1L);
        u2.setName("Julia Maria");
        u2.setCpf("09871843210");
        u2.setEmail("Julia@email.com");
        u2.setPassword("Galinha2");

        User u3 = new User();
        u3.setId(2L);
        u3.setName("Caue Urbini");
        u3.setCpf("09876543210");
        u3.setEmail("Caue@email.com");
        u3.setPassword("Galinha7");

        // Verificações
        assertEquals(u1.getId(), u2.getId());
        assertNotEquals(u3.getId(), u2.getId());
        assertEquals(u1.getId().hashCode(), u2.getId().hashCode());
        assertNotEquals(u1.getId().hashCode(), u3.getId().hashCode());
    }

}