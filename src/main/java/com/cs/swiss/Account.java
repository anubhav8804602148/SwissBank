package com.cs.swiss;

import javax.persistence.*;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long account_number;	

	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false)
	private boolean isActive=true;

	@Column(nullable=false)
	private String createdDate;
	
	@Column(nullable=true)
	private String closureDate;
	
	@Column(nullable=false)
	private double balance;
	
	@Column(nullable=false)
	private String category;


	public Account(String userId) {
		super();
		this.userId = userId;
		this.isActive = true;
		this.createdDate = java.time.LocalDateTime.now().toString();
		this.balance = 0;
		this.category = "REG";
	}
	public Account() {
		this.isActive = true;
		this.createdDate = java.time.LocalDateTime.now().toString();
		this.balance = 0;
		this.category = "REG";		
	}
	public long getAccount_number() {
		return account_number;
	}

	public void setAccount_number(long account_number) {
		this.account_number = account_number;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean validate() {	
		return true;
	}
}
