package com.a3bradesco.chat.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

// Classe que representa a mensagem trocada entre os usuários no chat

public class ChatMessage {

    private String content; // Conteúdo da mensagem
    private String sender; // Remetente
    private MessageType type; // Tipo da mensagem (CHAT, JOIN, LEAVE)
}
