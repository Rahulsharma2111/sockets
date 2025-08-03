package com.webSocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionService {
    
    private final Map<String, String> userSessionMap = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketSessionService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void registerUser(String username, String sessionId) {
        userSessionMap.put(username, sessionId);
    }

    public void sendPrivateMessage(String from, String to, String content) {
        String sessionId = userSessionMap.get(to);
        if (sessionId != null) {
            Message message = new Message(from, to, content, LocalDateTime.now());
            messagingTemplate.convertAndSendToUser(
                sessionId, 
                "/queue/messages", 
                message
            );
        }
    }
}