package com.a3bradesco.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // Informa ao Spring que essa é uma classe de configuração
@EnableWebSocketMessageBroker // Habilita o uso do WebSocket com STOMP
public class WebSocketConf implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registra o endpoint WebSocket que os clientes usarão para se conectar
        // withSockJS permite fallback para navegadores mais antigos
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // Prefixo usado pelo cliente para enviar mensagens para o servidor
        registry.setApplicationDestinationPrefixes("/app");

        // Prefixo usado para enviar mensagens do servidor para os clientes
        registry.enableSimpleBroker("/topic");
    }
}