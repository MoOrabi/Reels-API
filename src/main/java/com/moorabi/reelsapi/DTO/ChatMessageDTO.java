package com.moorabi.reelsapi.DTO;

import java.util.Date;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {

	private UserDTO sender;
	
	private UserDTO receiver;
	
	private Date sentAt;
	
	MessageType type; 
	
	String content;
	
	private Date readAt;
}
