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
	
	@Column(nullable=false,unique=true)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String designation="CUSTOMER";
	
	@Column(nullable=false)
	private String role="USER";
	
	@Column
	private String fathersName;
	
	@Column
	private String address;
	
	@Column
	private String currentAddress;
	
	@Column
	private String aadhaarNumber;
	
	@Column
	private String zipCode;
	
	@Column
	private String maritialStatus;
	
	@Column
	private String gender;
	
	@Column
	private String qualification;
	
	@Column
	private String occupation;
	
	@Column
	private double salary=0;
	
	@Column
	private String tempString="";

	@Column
	private String image;
	
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
	
	

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMaritialStatus() {
		return maritialStatus;
	}

	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getTempString() {
		return tempString;
	}

	public void setTempString(String tempString) {
		this.tempString = tempString;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getPhotosImagePath() {
		if(image==null) return "default";
		return "/userImage/"+id+"/"+image;
	}

	@Override
	public String toString() {
		return toString(false);
	}
	public String toString(boolean showPass) {
		String userString="{";
		userString+= "\nUser Id: %d,";
		userString+= "\nName: %s,";
		userString+= "\nEmail: %s,";
		userString+= "\nPassword: %s,";
		userString+= "\nDesignation: %s,";
		userString+= "\nAadhaar Number: %s,";
		userString+= "\nPresent Address: %s,";
		userString+= "\nPermanent Address: %s,";
		userString+= "\nFather's Name: %s,";
		userString+= "\nGender: %s,";
		userString+= "\nMaritial Status: %s,";
		userString+= "\nOccupation: %s,";
		userString+= "\nQualification: %s,";
		userString+= "\nRole: %s,";
		userString+= "\nSalary: %f,";
		userString+= "\nZip Code: %s";
		userString+= "\n}";
		return String.format(
			userString,
			this.id,
			this.fname+this.lname,
			this.email,
			showPass?this.password:"Hidden",
			this.designation,
			this.aadhaarNumber,
			this.currentAddress,
			this.address,
			this.fathersName,
			this.gender,
			this.maritialStatus,
			this.occupation,
			this.qualification,
			this.role,
			this.salary,
			this.zipCode
			);
	}
}
