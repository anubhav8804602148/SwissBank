package com.cs.swiss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ApplicationController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	TransactionRepository transactionRepo;
	@Autowired
	AccountRepository accountRepo;

	@GetMapping("")
	public String welcomeUser() {
		return "welcome";
	}
	@GetMapping("/register")
	public String showRegistrationPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/processRegistration")
	public String processRegistrationForm(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		try{
			userRepo.save(user);
			return "registerSuccess";
		}
		catch(Exception ex) {
			return "error";
		}
	}
	
	@GetMapping("/error")
	public String showError() {
		return "error";
	}
	
	@GetMapping("/login")
	public String showLoginPage(Model model, String error, String logout) {
		model.addAttribute("user", new User());
		model.addAttribute("error", error);
		model.addAttribute("msg",logout);
		return "login";
	}
	
}
