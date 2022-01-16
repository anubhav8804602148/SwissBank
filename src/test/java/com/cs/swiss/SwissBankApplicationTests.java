package com.cs.swiss;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
class SwissBankApplicationTests {

	private int port=443;
	private String host="https://127.0.0.1";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Test
	public void checkLoginPageAvailability() {
		try {
			this.mockMvc.perform(MockMvcRequestBuilders.get(host+port));
		} catch (Exception e) {
			assert(1==0);
		}
	}
	
}
