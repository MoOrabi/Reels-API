package com.moorabi.reelsapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.moorabi.reelsapi.util.SignalingSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketsConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(signalingSocketHandler(), "/room");
    }

    @Bean
    public WebSocketHandler signalingSocketHandler() {
        return new SignalingSocketHandler();
    }
}