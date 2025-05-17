package com.a3bradesco.chat.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.a3bradesco.chat.chat.ChatMessage;

public class WebSocketEventListenerTest {

    @Test
    void testHandleWebSocketDisconnectListener_withMock() {
        // mock do template
        SimpMessageSendingOperations messagingTemplate = mock(SimpMessageSendingOperations.class);
        WebSocketEventListener listener = new WebSocketEventListener(messagingTemplate);

        // mock do evento
        SessionDisconnectEvent mockEvent = mock(SessionDisconnectEvent.class);
        Message<byte[]> mockMessage = mock(Message.class);

        // cria headers simulados
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("username", "Fred");

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("simpSessionAttributes", sessionAttributes);

        MessageHeaders headers = new MessageHeaders(headerMap);

        when(mockEvent.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getHeaders()).thenReturn(headers);

        // executa o método
        assertDoesNotThrow(() -> listener.handleWebSocketDisconnectListener(mockEvent));

        // verifica se o envio foi chamado
        verify(messagingTemplate).convertAndSend(eq("/topic/public"), any(ChatMessage.class));
    }

    @Test
    void testHandleWebSocketDisconnectListener_withoutUsername() {
        SimpMessageSendingOperations messagingTemplate = mock(SimpMessageSendingOperations.class);
        WebSocketEventListener listener = new WebSocketEventListener(messagingTemplate);

        // Evento mockado sem username
        SessionDisconnectEvent mockEvent = mock(SessionDisconnectEvent.class);
        Message<byte[]> mockMessage = mock(Message.class);

        Map<String, Object> sessionAttributes = new HashMap<>(); // sem "username"
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("simpSessionAttributes", sessionAttributes);

        MessageHeaders headers = new MessageHeaders(headerMap);

        when(mockEvent.getMessage()).thenReturn(mockMessage);
        when(mockMessage.getHeaders()).thenReturn(headers);

        // Executa o método — deve passar sem lançar exceção
        assertDoesNotThrow(() -> listener.handleWebSocketDisconnectListener(mockEvent));

        // Verifica que NÃO enviou nenhuma mensagem
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(ChatMessage.class));
    }
}
