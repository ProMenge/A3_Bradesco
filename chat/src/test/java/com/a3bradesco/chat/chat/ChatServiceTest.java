package com.a3bradesco.chat.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public class ChatServiceTest {

    private final ChatService chatService = new ChatService();

    @Test
    void testProcessMessage_ValidInput() {
        ChatMessage input = new ChatMessage("Oi!", "Fred", MessageType.CHAT);
        ChatMessage result = chatService.processMessage(input);

        assertNotNull(result);
        assertEquals("Fred", result.getSender());
        assertEquals("Oi!", result.getContent());
        assertEquals(MessageType.CHAT, result.getType());
    }

    @Test
    void testRegisterUser_AddsUsernameToSession() {
        // Simulando os headers da sessão WebSocket
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
        accessor.setSessionAttributes(new HashMap<>());

        ChatMessage input = new ChatMessage("Entrando!", "Fred", MessageType.JOIN);
        ChatMessage result = chatService.registerUser(input, accessor);

        assertEquals("Fred", accessor.getSessionAttributes().get("username"));
        assertEquals("Fred", result.getSender());
    }
}
