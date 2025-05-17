package com.a3bradesco.chat.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebSocketConfTest {

    @Autowired
    private WebSocketConf webSocketConf;

    @Test
    void testWebSocketConfLoads() {
        assertNotNull(webSocketConf);
    }
}
