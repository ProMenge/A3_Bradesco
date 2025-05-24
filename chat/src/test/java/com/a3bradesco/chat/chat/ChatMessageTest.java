package com.a3bradesco.chat.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ChatMessageTest {

    @Test
    void testChatMessageFields() {
        ChatMessage message = new ChatMessage();
        message.setSender("Fred");
        message.setContent("Olá mundo!");
        message.setType(MessageType.CHAT);

        assertEquals("Fred", message.getSender());
        assertEquals("Olá mundo!", message.getContent());
        assertEquals(MessageType.CHAT, message.getType());
    }

    @Test
    void testChatMessageWithConstructor() {
        ChatMessage msg = new ChatMessage("Oi!", "Fred", MessageType.JOIN);

        assertNotNull(msg);
        assertEquals("Fred", msg.getSender());
        assertEquals("Oi!", msg.getContent());
        assertEquals(MessageType.JOIN, msg.getType());
    }
}
