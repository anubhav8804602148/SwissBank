package com.cs.swiss;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SwissBankApplicationTests {

	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	UserRepository userRepo;
	
}
