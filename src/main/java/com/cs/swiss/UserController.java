package com.cs.swiss;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value="/users", method=RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers(){
		return userRepo.findAll();
	}

	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public @ResponseBody User getUserById(@PathVariable("id") int id) {
		return userRepo.findById(id).get(0);
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT)
	public @ResponseBody HttpStatus putUserById(
			@PathVariable("id") int id, 
			@RequestParam("key") String key, 
			@RequestParam("value") String value) {
		User user = userRepo.findById(id).get(0);
		switch(key) {
		case "role":
			user.setRole(value);
			break;
		case "designation":
			user.setDesignation(value);
			break;
		case "email":
			user.setEmail(value);
			break;
		case "fname":
			user.setFname(value);
			break;
		case "lname":
			user.setLname(value);
			break;
		case "password":
			user.setPassword(value);
			break;
		default:
			return HttpStatus.BAD_REQUEST;
		}
		return HttpStatus.ACCEPTED;
		
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.POST)
	public @ResponseBody HttpStatus postUserById(
			@PathVariable("id") int id,
			@RequestParam("role") String role,
			@RequestParam("designation") String designation,
			@RequestParam("email") String email,
			@RequestParam("fname") String fname,
			@RequestParam("lname") String lname,
			@RequestParam("password") String password
			) {
		User user = new User();
		user.setDesignation(designation);
		user.setEmail(email);
		user.setFname(fname);
		user.setId(id);
		user.setLname(lname);
		user.setPassword(password);
		user.setRole(role);
		userRepo.save(user);
		return HttpStatus.ACCEPTED;
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
	public @ResponseBody HttpStatus deleteUserById(@PathVariable("id") int id) {
		userRepo.deleteById(id);
		return HttpStatus.OK;
	}	
}
