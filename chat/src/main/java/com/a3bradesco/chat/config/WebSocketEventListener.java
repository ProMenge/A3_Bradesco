package com.a3bradesco.chat.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.a3bradesco.chat.chat.ChatMessage;
import com.a3bradesco.chat.chat.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component // Define como um bean gerenciado pelo Spring
@RequiredArgsConstructor // Permite injeção de dependências via construtor
@Slf4j // Habilita uso do logger: log.info(), log.error(), etc.

public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener // Marca o método como ouvinte de eventos — nesse caso, escuta desconexões
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        // Extrai os cabeçalhos STOMP da mensagem de desconexão
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Recupera o nome do usuário armazenado na sessão WebSocket
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        // Se o nome estiver presente, envia uma notificação para todos os clientes
        // conectados
        if (username != null) {
            // Registra no log que o usuário se desconectou
            log.info("User Disconnected: {}", username);

            // Cria uma mensagem de saída (tipo LEAVE) para notificar o front-end
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            // Envia a mensagem para todos os clientes que estão inscritos no canal
            // /topic/public
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}