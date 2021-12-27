package com.cs.swiss;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	TransactionRepository transactionRepo;
	
	@RequestMapping(value="/transactions",method=RequestMethod.GET)
	public List<Transaction> getAllTransactions(){
		return transactionRepo.findAll();
	}
	
	@RequestMapping(value="/transactions/{id}", method=RequestMethod.GET)
	public @ResponseBody Transaction getTransactionById(@PathVariable long id) {
		return transactionRepo.findById(id).get(0);
	}
	
	 @RequestMapping(value="/transaction/{id}", method=RequestMethod.POST)
	 public @ResponseBody HttpStatus postTransactionByid(
			 @PathVariable("id") long id,
			 @RequestParam("accountNumber") long accountNumber,
			 @RequestParam("approved") boolean approved,
			 @RequestParam("status") String status,
			 @RequestParam("approvalRequired") boolean approvalRequired,
			 @RequestParam("creditedAmount") double creditedAmount,
			 @RequestParam("debitedAmount") double debitedAmount,
			 @RequestParam("type") String type,
			 @RequestParam("userId") String userId,
			 @RequestParam("password") String password			 
			 ){
		 
		 Transaction transaction = new Transaction();
		 transaction.setAccountNumber(accountNumber);
		 transaction.setApprovalRequired(approvalRequired);
		 transaction.setCreditedAmount(creditedAmount);
		 transaction.setDebitedAmount(debitedAmount);
		 transaction.setStatus(status);
		 transaction.setDateOfTransaction(java.time.LocalDateTime.now().toString());
		 transaction.setType(type);
		 transaction.setApproved(approved);
		 
		 Account account = accountRepo.findByAccountNumber(transaction.getAccountNumber()).get(0);
		 User user = userRepo.findByEmail(account.getUserId()).get(0);
		 
		 //check balance
		 account.setBalance(account.getBalance()-transaction.getDebitedAmount()+transaction.getCreditedAmount());
		 if(account.getBalance()<0) return HttpStatus.EXPECTATION_FAILED;
		 
		 //verify password
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		 String encodedPassword = encoder.encode(password);
		 if(!encodedPassword.equals(user.getPassword())) return HttpStatus.FORBIDDEN;
		 
		 //check approval
		 if(transaction.isApproved()|| !transaction.isApprovalRequired()) accountRepo.save(account);
		 
		 transactionRepo.save(transaction);
		 return HttpStatus.ACCEPTED;
	 }
}
