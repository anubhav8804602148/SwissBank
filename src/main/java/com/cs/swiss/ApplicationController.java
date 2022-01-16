package com.cs.swiss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ApplicationController {
	/*
	 *  "/"
	 *  "/register"
	 *  "/processRegistration"
	 *  "/error"
	 *  "/login"
	 */

	@Autowired 
	UserLogRepository userLogRepo;
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	TransactionRepository transactionRepo;
	@Autowired
	AccountRepository accountRepo;

	@GetMapping("")
	public String welcomeUser(Model model) {
		model.addAttribute("user", userRepo.findByEmail(
				SecurityContextHolder.getContext().getAuthentication().getName()).get(0));
		return "welcome";
	}
	@GetMapping("/register")
	public String showRegistrationPage(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/processRegistration")
	public String processRegistrationForm(User user, Model model, @RequestParam("userImage") MultipartFile multipartFile) {
		System.out.println(
				java.time.LocalDateTime.now().toString()+
				"] Processing for "+user.getEmail());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		UserLog userLog = new UserLog();
		userLog.setCreatedDate(java.time.LocalDateTime.now().toString());
		userLog.setEmail(user.getEmail());
		userLog.setLastUpdatedDate(java.time.LocalDateTime.now().toString());
		userLog.setLastUpdatedUserId(
			SecurityContextHolder.getContext().getAuthentication().getName()
		);
		userLog.setLastPassword(user.getPassword());
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setImage(fileName);
		FileUploadUtil.saveFile("userImage/",fileName, multipartFile);
		
		try{
			userRepo.save(user);
			userLogRepo.save(userLog);
		}
		catch(Exception ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			return "error";
		}
		CustomEmailService.sendmail("User Registered : "+userRepo.findByEmail(user.getEmail()).get(0).getId(), user.getEmail(), user.toString());
		return "registerSuccess";
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
