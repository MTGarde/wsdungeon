package com.wsdungeon.dungeon.config;

import com.wsdungeon.dungeon.SocketConnectionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(new SocketConnectionHandler(), "/hello").setAllowedOrigins("*");

    }
}
