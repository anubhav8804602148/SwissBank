package com.cs.swiss;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TransactionController {
	
	/*
	 *  "/transactions"
	 *  "/transactions{id}"
	 */
	
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private TransactionRepository transactionRepo;
	@Autowired
	private UserRepository userRepo;

	@RequestMapping(value="/transactions",method=RequestMethod.GET)
	public @ResponseBody List<Transaction> getAllTransactions(){
		return transactionRepo.findAll();
	}
	
	@RequestMapping(value="/transactions/{id}", method=RequestMethod.GET)
	public @ResponseBody Transaction getTransactionById(@PathVariable long id) {
		return transactionRepo.findById(id).get(0);
	}
	
	 @RequestMapping(value="/transactions/{id}", method=RequestMethod.POST)
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
	 
	 @RequestMapping(value="/transactionsSummary", method=RequestMethod.GET)
	 public String showTransactionDetails(Model model) {
		 String email = SecurityContextHolder.getContext().getAuthentication().getName();
		 List<Transaction> transactions = new ArrayList<Transaction>();
		 for(Account account : accountRepo.findByUserId(email)) {
			 transactions.addAll(transactionRepo.findByAccountNumber(account.getAccount_number()));
		 }
		 model.addAttribute("transactions", transactions);
		 model.addAttribute("countOfTransaction", transactions.size());
		 return "TransactionDetails";
		 
	 }

		@RequestMapping(value="/transferFunds",method=RequestMethod.GET)
		public String showFundTransferPage(Model model) {
			model.addAttribute("accounts",
					accountRepo.findByUserId(SecurityContextHolder.getContext()
									.getAuthentication()
									.getName()
					)
					.stream()
					.map(acc -> acc.getAccount_number())
					.collect(Collectors.toList())				
			);
			model.addAttribute("transaction", new Transaction());
			return "MakePayment";
		}	
		
		@RequestMapping(value="/processTransaction",method=RequestMethod.POST)
		public String processTransaction(Transaction transaction) {
			Account fromAccount = accountRepo.findByAccountNumber(transaction.getFromAccountNumber()).get(0);
			long toAccountNumber = transaction.getAccountNumber();
			Account toAccount = null;
			try{
				toAccount = accountRepo.findByAccountNumber(toAccountNumber).get(0);
			}
			catch(Exception ex) {
				//External processing
				return "error";
			}
			Transaction toTransaction = new Transaction();
			if(!transaction.getIFSCCode().equals("SWBA0000123")) return "error";
			
			toTransaction.setFromAccountNumber(transaction.getFromAccountNumber());
			toTransaction.setAccountNumber(transaction.getAccountNumber());
			toTransaction.setDateOfTransaction(java.time.LocalDateTime.now().toString());
			toTransaction.setCreditedAmount(transaction.getDebitedAmount());
			toTransaction.setIFSCCode("SWBA0000123");
			toTransaction.setStatus("COMPLETED");
			toTransaction.setType("IMPS");
			
			transaction.setType("IMPS");
			transaction.setStatus("COMPLETED");
			transaction.setDateOfTransaction(java.time.LocalDateTime.now().toString());
			toAccount.setBalance(toAccount.getBalance()+transaction.getDebitedAmount());
			fromAccount.setBalance(fromAccount.getBalance()-transaction.getDebitedAmount());
			transactionRepo.save(transaction);
			accountRepo.save(toAccount);
			accountRepo.save(fromAccount);
			return "TransactionDetails";
		}
}
