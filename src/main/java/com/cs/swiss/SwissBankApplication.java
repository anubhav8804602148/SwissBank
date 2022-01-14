package com.cs.swiss;

import org.springframework.boot.SpringApplication;    
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cs.swiss.externalTransactions.TransactionHandler;
@SpringBootApplication
public class SwissBankApplication {
	
	public static void main(String[] args) {
		new Thread(()->{
			TransactionHandler.runHandler();
		}).start();
		SpringApplication.run(SwissBankApplication.class, args);
	}
}
