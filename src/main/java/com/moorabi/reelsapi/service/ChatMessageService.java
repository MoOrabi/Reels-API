package com.moorabi.reelsapi.service;

import org.springframework.stereotype.Service;

import com.moorabi.reelsapi.DTO.ChatMessageDTO;
import com.moorabi.reelsapi.model.ChatMessage;
import com.moorabi.reelsapi.util.UserUtil;

@Service
public class ChatMessageService {
	
	public ChatMessageDTO sendMessage(ChatMessage chatMessage) {
        return new ChatMessageDTO(UserUtil.convertToDTO(chatMessage.getKey().getSender()), 
        		UserUtil.convertToDTO(chatMessage.getKey().getReciever()),
        		chatMessage.getKey().getSentAt(), chatMessage.getType(), 
        		chatMessage.getContent(), chatMessage.getReadAt());
    }
	
	public ChatMessageDTO addUser(ChatMessage chatMessage) {
        return new ChatMessageDTO(UserUtil.convertToDTO(chatMessage.getKey().getSender()), 
        		null,
        		chatMessage.getKey().getSentAt(), chatMessage.getType(), 
        		chatMessage.getContent(), chatMessage.getReadAt());
    }

}
