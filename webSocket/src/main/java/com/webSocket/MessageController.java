package com.webSocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageController {

    @MessageMapping("/chat") // Handles messages sent to /app/chat
    @SendTo("/topic/messages") // Sends the return value to /topic/messages
    public Message sendMessage(Message message) {
        // You can process the message here (save to DB, etc.)
        return message;
    }


        @MessageMapping("/chat.send")
        @SendToUser("/queue/messages") // Send to specific user's queue
        public Message sendPrivateMessage(
                @Payload Message message,
                Principal principal) {

            // You can add message processing logic here
            message.setFrom(principal.getName());
            return message;
        }

        @MessageMapping("/chat.register")
        public void registerUser(@Payload String username, SimpMessageHeaderAccessor headerAccessor) {
            String sessionId = headerAccessor.getSessionId();
            // Associate username with session (you might want to store this in a service)
            // This is needed to map usernames to WebSocket sessions
    }
}