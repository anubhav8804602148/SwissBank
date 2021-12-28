package com.cs.swiss;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SwissBankApplicationTests {

	@LocalServerPort 
	private int port;
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Test
	public void loadData() throws Exception {
		Account account;
		List<User> userList = userRepo.findAll().stream().filter(u -> u.getRole().equals("CUSTOMER")).collect(Collectors.toList());
		for(User user : userList) {
			account = new Account(user.getEmail());
			accountRepo.save(account);
		}
	}
	
	
	@Autowired
	private ApplicationController applicationController;
	
	@Autowired
	private TestRestTemplate template;
	
	@Test
	private void loadSomeData() {
		SpringApplication.run(SwissBankApplication.class, new String[] {"dummy", "arguments"});
	}
	
	@Test
	private void guiLoading() {
		assertThat(this.template.getForObject("https://127.0.0.1:"+port+"/login", String.class)).contains("Swiss");
	}
	
	@Test
	private void controllersAreNotNull() {
		assertThat(applicationController).isNotNull();
	}
	
	@Test
	private void HttpStatusFromAllPagesLooksGood() {
		
	}

}
