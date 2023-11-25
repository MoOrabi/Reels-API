package com.moorabi.reelsapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moorabi.reelsapi.model.ChatGroupMessage;
import com.moorabi.reelsapi.model.ChatMessage;

@Repository
public interface ChatGroupMessageRepository extends JpaRepository<ChatGroupMessage, Long> {

	
	@Query("select M from ChatGroupMessage M "
			+ "where M.key.group.id=:groupid order by M.key.sentAt")
	List<ChatMessage> findForGroupChat(Integer groupid);

}
