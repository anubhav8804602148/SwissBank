package com.cs.swiss;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AccountController {
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping("/accounts")
	public @ResponseBody List<Account> getAccounts(){
		return accountRepo.findAll();
	}

	@RequestMapping(value="/accounts/{id}", method=RequestMethod.GET)
	public @ResponseBody Account getAccountById(@PathVariable("id") int id) {
		return accountRepo.findByAccountNumber(id).get(0);
	}
	
	@RequestMapping(value="/accounts/{id}", method=RequestMethod.PUT)
	public @ResponseBody HttpStatus putAccountById(@PathVariable("id") int id, @RequestParam String key, @RequestParam String value) {
		Account account = accountRepo.findByAccountNumber(id).get(0);
		switch(key) {
		case "isActive":
			account.setActive(Boolean.parseBoolean(value));
			break;
		case "balance":
			account.setBalance(Double.parseDouble(value));
			break;
		case "category":
			account.setCategory(value);
			break;
		case "closureDate":
			account.setClosureDate(value);
			account.setActive(false);
			break;
		default:
			return HttpStatus.BAD_REQUEST;
			
		}
		if(account.validate()) {
			accountRepo.save(account);
			return HttpStatus.ACCEPTED;
		}
		return HttpStatus.BAD_REQUEST;
		
	}
	
	@RequestMapping(value="/accounts/{id}", method=RequestMethod.POST)
	public @ResponseBody HttpStatus postAccountById(
			@PathVariable("id") int id, 
			@RequestParam("balance") double balance,
			@RequestParam("category") String category,
			@RequestParam("userId") String userId) {
		if(userRepo.findByEmail(userId).size()==0) return HttpStatus.NOT_ACCEPTABLE;
		Account account = new Account();
		account.setBalance(balance);
		account.setCategory(category);
		account.setUserId(userId);
		account.setAccount_number(id);
		accountRepo.save(account);
		return HttpStatus.ACCEPTED;
	}
	
	@RequestMapping(value="/accounts/{id}", method=RequestMethod.DELETE)
	public @ResponseBody HttpStatus deleteAccountById(@PathVariable("id") long id) {
		accountRepo.deleteById(id);
		return HttpStatus.OK;
	}
	
	
}
