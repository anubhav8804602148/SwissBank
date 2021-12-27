package com.cs.swiss;

import org.springframework.http.HttpEntity; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class ApplicationController {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@RequestBody String request) {
		System.out.println("Received : "+request);
		return "hello";
	}

	@RequestMapping(value="/processLogin", method=RequestMethod.POST)
	public String processLogin(@RequestBody String request) {
		System.out.println("Received : "+request);
		return "hello";
	}
	
	@RequestMapping("/")
	public @ResponseBody String greetAndCheckConnection() {
		return "Welcome to Swiss Bank!!";
	}
}
