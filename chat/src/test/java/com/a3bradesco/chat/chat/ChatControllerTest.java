package com.a3bradesco.chat.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

    @LocalServerPort
    private int port;

    private final static String WS_URI = "ws://localhost:%d/ws";

    @Test
    public void testChatMessageFlow() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        CompletableFuture<ChatMessage> future = new CompletableFuture<>();

        StompSession session = stompClient
                .connect(String.format(WS_URI, port), new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/public", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                future.complete((ChatMessage) payload);
            }
        });

        ChatMessage message = new ChatMessage();
        message.setSender("Fred");
        message.setContent("Olá, mundo!");
        message.setType(MessageType.CHAT);

        session.send("/app/chat.sendMessage", message);

        ChatMessage response = future.get(5, TimeUnit.SECONDS);
        assertEquals("Fred", response.getSender());
        assertEquals("Olá, mundo!", response.getContent());
        assertEquals(MessageType.CHAT, response.getType());
    }
}
