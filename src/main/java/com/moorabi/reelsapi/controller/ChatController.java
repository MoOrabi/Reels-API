package com.moorabi.reelsapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.moorabi.reelsapi.DTO.ChatMessageDTO;
import com.moorabi.reelsapi.model.ChatMessage;
import com.moorabi.reelsapi.service.ChatMessageService;

@Controller
public class ChatController {

	@Autowired
	private ChatMessageService chatMessageService;
	
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDTO sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessageService.sendMessage(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDTO addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getKey().getSender().getUsername());
        return chatMessageService.addUser(chatMessage);
    }
}
