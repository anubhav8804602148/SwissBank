package com.cs.swiss;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> userList = userRepo.findByEmail(username);
		if(userList.size()<1) throw new UsernameNotFoundException("No such User");
		return new CustomUserDetails(userList.get(0));
	}

}