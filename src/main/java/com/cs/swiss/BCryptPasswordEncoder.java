package com.cs.swiss;

public class BCryptPasswordEncoder {

	public String encode(String password) {
		return password.hashCode()+"";
	}

}
