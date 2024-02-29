package com.moorabi.reelsapi.compositekeys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.moorabi.reelsapi.model.AppUser;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class ChatMessageKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private AppUser sender;
	
	@ManyToOne
	private AppUser reciever;
	
	@Column(nullable = false)
	private Date sentAt;
}
