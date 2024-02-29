package com.moorabi.reelsapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

	@Query("select M from ChatMessage M "
			+ "where (M.key.sender.id=:from and M.key.reciever.id=:to) "
			+ "or (M.key.sender.id=:to and M.key.reciever.id=:from) ")
	List<ChatMessage> findForPersonalChat(Integer from, Integer to);
	
	

}
