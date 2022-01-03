package com.cs.swiss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Feedback {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=true)
	private String userId;
	
	@Column(nullable=true)
	private int rating;
	
	@Column(nullable=true)
	private String message;

	@Column(nullable=true)
	private boolean consentForSharingUserId;
	
	@Column(nullable=false)
	private String category;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isConsentForSharingUserId() {
		return consentForSharingUserId;
	}

	public void setConsentForSharingUserId(boolean consentForSharingUserId) {
		this.consentForSharingUserId = consentForSharingUserId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
