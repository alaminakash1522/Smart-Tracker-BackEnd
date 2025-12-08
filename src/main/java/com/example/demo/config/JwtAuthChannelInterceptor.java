// ===== NEW CLASS: JwtAuthChannelInterceptor (for WebSocket) =====
package com.example.demo.config;

import com.example.demo.services.JwtService;
import com.example.demo.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthChannelInterceptor implements ChannelInterceptor {

    // === your existing services reused here ===
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthChannelInterceptor(JwtService jwtService,
                                     MyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // Only authenticate on WebSocket CONNECT
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {

            // JWT is expected in STOMP native header "Authorization"
            // same format: "Bearer <token>"
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);
            String username = jwtService.extractUserName(token); // <--- uses your method

            if (username == null) {
                throw new IllegalArgumentException("Invalid JWT token: no username");
            }

            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (!jwtService.validateToken(token, userDetails)) {
                throw new IllegalArgumentException("Invalid JWT token");
            }

            // Attach authenticated user to WebSocket session
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            accessor.setUser(auth);
        }

        return message;
    }
}

