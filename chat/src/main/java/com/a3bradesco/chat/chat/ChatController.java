package com.a3bradesco.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller // Define que essa classe é um controlador Spring (componente do MVC)
public class ChatController {

    @MessageMapping("/chat.sendMessage") // Mapeia mensagens enviadas para /app/chat.sendMessage
    @SendTo("/topic/public") // Define que a resposta será enviada para todos usuários no /topic/public

    // Metodo para o envio de mensagens
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")

    // Método para adicionar usuário à sessão WebSocket e notificar o canal
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor) {
        // Adiciona o nome do usuário durante a conexão
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
