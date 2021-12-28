package com.cs.swiss.bulkUploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cs.swiss.User;
import com.cs.swiss.UserRepository;

public class UserBulkUpload {
	
	@Autowired
	UserRepository userRepo;
	public User user;
	
	UserBulkUpload() throws FileNotFoundException{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		File file = new File("D:\\my_projects\\SwissBank\\src\\main\\java\\com\\cs\\swiss\\bulkUploader\\UserList.csv");
		Scanner in = new Scanner(file);
		while(in.hasNext()) {
			user = new User();
			String line = in.nextLine();
			String[] allFields = line.split(",");
			user.setFname(allFields[1]);
			user.setLname(allFields[2]);
			user.setEmail(allFields[3]);	
			user.setPassword(encoder.encode(allFields[4]));
			String cat = allFields[5];
			if(cat.contains("AB-")) {
				user.setDesignation("ADMIN");
				user.setRole("COOWNER");
			}
			else if(cat.contains("B-")) {
				user.setDesignation("ADMIN");
				user.setRole("APP_OWNER");
			}
			else if(cat.contains("B+")) {
				user.setDesignation("USER");
				user.setRole("APP_DEV");
			}
			else if(cat.contains("A")) {
				user.setDesignation("STAFF");
				user.setRole("APP_SUPPORT");
			}
			else {
				user.setDesignation("CUSTOMER");
				user.setRole("USER");
			}
			userRepo.save(user);
		}
		in.close();
	}
	public static void main(String ars[]) throws FileNotFoundException {
		new UserBulkUpload();
	}
}
