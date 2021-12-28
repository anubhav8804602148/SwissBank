package com.cs.swiss;

import javax.persistence.Column;  
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String fname;
	
	@Column(nullable=false)
	private String lname;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String designation="CUSTOMER";
	
	@Column(nullable=false)
	private String role="USER";

	public User(){
		
	}
	
	public User(String fname, String lname, String email, String password) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	public String toString() {
		return toString(false);
	}
	public String toString(boolean showPass) {
		if(showPass) return String.format(
			"{Id: %d, fname: %s, lname: %s, email: %s, password: %s, designation: %s, role: %s}",
			this.id,
			this.fname,
			this.lname,
			this.email,
			this.password,
			this.designation,
			this.role);
		return String.format(
				"{id: %d, fname: %s, lname: %s, email: %s, designation: %s, role: %s}",
			this.id,
			this.fname,
			this.lname,
			this.email,
			this.designation,
			this.role);
	}
}
