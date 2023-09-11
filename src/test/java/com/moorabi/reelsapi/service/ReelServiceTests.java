package com.moorabi.reelsapi.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.moorabi.reelsapi.model.Reel;
import com.moorabi.reelsapi.DTO.ReelDTO;
import com.moorabi.reelsapi.repository.ReelRepository;
import com.moorabi.reelsapi.util.ReelUtil;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class ReelServiceTests {

	@InjectMocks
	ReelService reelService;
	
	Reel reel,reel2;
	ReelDTO reelDTO,reelDTO2;
	@Mock
	ReelRepository reelRepository;
	
//	@BeforeEach
//	void setUp() {
//		reelService = new ReelService(reelRepository);
//	}
	
	
	@Test
	public void getAllReels() throws IOException {
		File videoFile = new File("E:\\mohamed\\Stuff\\Photos ^_^\\123517258_190551782561059_7379908263782341326_o.jpg") ;
		byte[] video= FileUtils.readFileToByteArray(videoFile);
		reel = new Reel(null, "Egypt", "Cairo", "First Reel", video);
		reel2 = new Reel(null, "UK", "London", "First Reel", video);
		List<Reel> reels= new ArrayList<>();
		reels.add(reel);
		reels.add(reel2);
		
		reelDTO = new ReelDTO(null, null, "Egypt", "Cairo", "First Reel", video, null, reel, null);
		reelDTO2 = new ReelDTO(null, null, "UK", "London", "First Reel", video, null, reel2, null);
		List<ReelDTO> reelsDTO= new ArrayList<>();
		reelsDTO.add(reelDTO);
		reelsDTO.add(reelDTO2);
		
		when(reelRepository.findAll()).thenReturn(reels);
//		try (MockedStatic<ReelUtil> reelUtils = mockStatic(ReelUtil.class)) {
//			reelUtils.when(() -> ReelUtil.convertAllToDTO(reels)).thenReturn(reelsDTO);
//		}
		System.out.println(reelService.getAllReels().size());
		assertTrue(reelService.getAllReels().size()==1);
		assertTrue(reelService.getAllReels().get(0).getCountry().equals("Egypt"));
		assertTrue(reelService.getAllReels().get(1).getCity().equals("London"));
	}
}
