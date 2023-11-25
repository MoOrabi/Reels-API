package com.moorabi.reelsapi.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MessageGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String name;
	
	@ManyToMany
	@JoinTable(
			  name = "group_members", 
			  joinColumns = @JoinColumn(name = "group_id"), 
			  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<AppUser> members;
	
	@Column
	@OneToMany(mappedBy = "key.group")
	private Set<ChatGroupMessage> messages;

}
