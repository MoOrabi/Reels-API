package com.moorabi.reelsapi.model;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.moorabi.reelsapi.compositekeys.ChatGroupMessageKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatGroupMessage extends Message {

	@EmbeddedId
	private ChatGroupMessageKey key;
	
	@Builder
	public ChatGroupMessage(MessageType type, String content,
   			AppUser sender, MessageGroup group,
   			Date sentAt, Date readAt) {
		super(type,content, readAt);
		this.key.setGroup(group);
		this.key.setSender(sender);
	    this.key.setSentAt(sentAt);
	}
}
