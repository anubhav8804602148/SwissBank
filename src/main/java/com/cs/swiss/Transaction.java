package com.cs.swiss;

import javax.persistence.*;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(nullable=false)
	private String type;
	
	@Column(nullable=false)
	private long fromAccountNumber;

	@Column(nullable=false)
	private String IFSCCode;
	
	@Column(nullable=false)
	private long accountNumber;

	@Column(nullable=true)
	private double creditedAmount;

	@Column(nullable=false)
	private double debitedAmount;

	@Column(nullable=false)
	private String dateOfTransaction;

	@Column(nullable=false)
	private String status;
	
	@Column(nullable=false)
	private boolean approved=true;

	@Column(nullable=false)
	private boolean approvalRequired=false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCreditedAmount() {
		return creditedAmount;
	}

	public void setCreditedAmount(double creditedAmount) {
		this.creditedAmount = creditedAmount;
	}

	public double getDebitedAmount() {
		return debitedAmount;
	}

	public void setDebitedAmount(double debitedAmount) {
		this.debitedAmount = debitedAmount;
	}

	public String getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(String dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isApprovalRequired() {
		return approvalRequired;
	}

	public void setApprovalRequired(boolean approvalRequired) {
		this.approvalRequired = approvalRequired;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(long fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getIFSCCode() {
		return IFSCCode;
	}

	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}
}
