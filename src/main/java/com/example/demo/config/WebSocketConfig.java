// ===== NEW OR UPDATED CLASS: WebSocketConfig =====
package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // --- NEW: inject the interceptor we just created ---
    private final JwtAuthChannelInterceptor jwtAuthChannelInterceptor;

    public WebSocketConfig(JwtAuthChannelInterceptor jwtAuthChannelInterceptor) {
        this.jwtAuthChannelInterceptor = jwtAuthChannelInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // === NEW: endpoints (you can tweak paths/origins) ===
        registry.addEndpoint("/ws")          // for React web (SockJS)
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();

        registry.addEndpoint("/ws-native")   // for React Native (pure WebSocket)
                .setAllowedOrigins("*");     // dev only – tighten later
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // === NEW: basic STOMP broker setup ===
        config.enableSimpleBroker("/topic");          // clients SUBSCRIBE here
        config.setApplicationDestinationPrefixes("/app"); // clients SEND here
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // --- NEW: hook JWT interceptor into inbound WebSocket messages ---
        registration.interceptors(jwtAuthChannelInterceptor);
    }
}
