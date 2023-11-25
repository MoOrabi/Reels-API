package com.moorabi.reelsapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.ChatGroupMessage;
import com.moorabi.reelsapi.model.ChatMessage;
import com.moorabi.reelsapi.model.MessageGroup;

@Repository
public interface MessageGroupRepository extends JpaRepository<MessageGroup, Long> {

	
	@Query("select G from MessageGroup G"
			+ " where :user in G.members")
	List<MessageGroup> findGroupsForUser(AppUser user);

}
