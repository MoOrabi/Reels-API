package com.moorabi.reelsapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.moorabi.reelsapi.DTO.UserDTO;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.ChatMessage;
import com.moorabi.reelsapi.model.MessageType;
import com.moorabi.reelsapi.repository.UserRepository;
import com.moorabi.reelsapi.util.UserUtil;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    
    private final UserRepository userRepository;
    
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        AppUser user = userRepository.findUserByUsername(username);
        UserDTO userDTO = UserUtil.convertToDTO(user);
        if (username != null) {
            log.info("user disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(user)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}
