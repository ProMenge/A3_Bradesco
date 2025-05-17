package com.a3bradesco.chat.chat;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public ChatMessage processMessage(ChatMessage chatMessage) {
        // Aqui você pode validar, filtrar, salvar no banco, etc.
        return chatMessage;
    }

    public ChatMessage registerUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
