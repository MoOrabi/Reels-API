package com.moorabi.reelsapi.service;

import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.util.ReelUtil;

public class ReelServiceTest {

	@Mock
	private ReelRepository reelRepository;
	@Mock
	private ReelUtil reelUtil;
	
	@Test
	void getAllReels() {
		verify(reelRepository).findAll();
		List<Reel> allReels= reelRepository.findAll(); 
	}
}
