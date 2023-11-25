package com.moorabi.reelsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.ChatGroupMessage;
import com.moorabi.reelsapi.model.ChatMessage;
import com.moorabi.reelsapi.repository.ChatGroupMessageRepository;
import com.moorabi.reelsapi.repository.ChatMessageRepository;

import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    @Autowired
    private ChatGroupMessageRepository chatGroupMessageRepository;

    public void sendMessage(ChatMessage message) {

        chatMessageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + message.getKey().getReceiver().getId(), message);

    }
    
    public void sendMessageGroup(ChatGroupMessage message) {

        chatGroupMessageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/topic/messages/group/" + message.getKey().getGroup().getId(), message);

    }

    public List<ChatMessage> getListMessage(@PathVariable("from") Integer from, @PathVariable("to") Integer to){
        return chatMessageRepository.findForPersonalChat(from, to);
    }


    public List<ChatMessage> getListMessageGroups(@PathVariable("groupid") Integer groupid){
        return chatGroupMessageRepository.findForGroupChat(groupid);
    }


    
}
