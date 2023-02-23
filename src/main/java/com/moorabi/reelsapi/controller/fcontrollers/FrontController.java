package com.moorabi.reelsapi.controller.fcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontController {

	public FrontController() {
		
	}
	
	@RequestMapping("/loginf")
    public String login() {
    	return "login";
    }
}
