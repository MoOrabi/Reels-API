package com.moorabi.reelsapi.compositekeys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.MessageGroup;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class ChatGroupMessageKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private MessageGroup group;
	
	@ManyToOne
	private AppUser sender;
	
	@Column(nullable = false)
	private Date sentAt;
}
