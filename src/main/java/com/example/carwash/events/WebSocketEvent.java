package com.example.carwash.events;

import com.example.carwash.model.chat.ChatMessage;
import com.example.carwash.model.chat.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEvent {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;



    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username!= null) {
            var chatMessage = new ChatMessage();
            chatMessage.setSender(username);
            chatMessage.setType(MessageType.LEAVE);

            messagingTemplate.convertAndSend("/chat/public", chatMessage);
        }
    }
}
