package com.moorabi.reelsapi.model;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import com.moorabi.reelsapi.compositekeys.ChatMessageKey;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage extends Message {

	
	@EmbeddedId
	private ChatMessageKey key;
	
    
    @Builder
    public ChatMessage(MessageType type, String content,
	 	   			AppUser sender, AppUser reciever,
	 	   			Date sentAt, Date readAt) {
	    super(type,content, readAt);
	    this.key.setReceiver(reciever);
	    this.key.setSender(sender);
	    this.key.setSentAt(sentAt);
    }
}
