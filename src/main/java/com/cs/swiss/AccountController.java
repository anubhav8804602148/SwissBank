package com.cs.swiss;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccountController {
	/*
	 *	"/accounts"
	 *	"/accounts/{id}"
	 *  "/accountSummary"
	 *  "/createNewAccount"
	 *  "/processNewAccount"
	 *  "/updateAccount"
	 *  "/processUpdateAccount"
	 */
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserLogRepository userLogRepo;

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
	

	@GetMapping("/accountSummary")
	public String showAccountSummary(Model model, String error, String logout) {
		model.addAttribute("user", userRepo.findByEmail(
				SecurityContextHolder.getContext().getAuthentication().getName()).get(0));
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("email", email);
		List<Account> accountList = accountRepo.findByUserId(email);
		model.addAttribute("countOfAccount", accountList.size());
		model.addAttribute("accountList",accountList);
		return "AccountSummary";
	}
	
	
	@RequestMapping(value="/createNewAccount", method=RequestMethod.GET)
	public String showNewAccountForm(Model model) {
		model.addAttribute("user", userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get(0));
		model.addAttribute("accountType", new String());
		return "createNewAccount";
	}
	
	@RequestMapping(value="/processNewAccount", method=RequestMethod.POST)
	public String processNewAccount(User user, Model model) {
		Account account = new Account(user.getEmail());
		String cat = user.getTempString();
		account.setCategory(user.getTempString());
		user.setTempString("");
		List<Account> existingAccounts = accountRepo
				.findByUserId(user.getEmail())
				.stream()
				.filter(acc -> acc.getCategory().equals(account.getCategory()))
				.collect(Collectors.toList());
		if(existingAccounts.size()>0) {
			model.addAttribute("errorMessage","You already have an account of the provided type");
			return "error";
		}
		UserLog userLog = new UserLog();
		userLog.setCreatedDate(userLogRepo.findUserLogByEmail(user.getEmail()).get(0).getCreatedDate());
		userLog.setEmail(user.getEmail());
		userLog.setLastUpdatedDate(java.time.LocalDateTime.now().toString());
		userLog.setLastUpdatedUserId(
				SecurityContextHolder.getContext().getAuthentication().getName()
		);
		user.setPassword(userRepo.findByEmail(user.getEmail()).get(0).getPassword());
		user.setImage(userRepo.findByEmail(user.getEmail()).get(0).getImage());
		userLog.setLastPassword(user.getPassword());
		userLogRepo.save(userLog);
		userRepo.save(user);
		accountRepo.save(account);
		Account newAccount = accountRepo.findByUserId(user
				.getEmail())
				.stream()
				.filter(acc -> acc.getCategory()
						.equals(cat))
				.collect(Collectors.toList()).get(0);
		CustomEmailService.sendmail("Account Created : "+newAccount.getAccount_number(), newAccount.getUserId(), user.toString());
		return "AccountSummary";
	}

	@RequestMapping(value="/updateAccount", method=RequestMethod.GET)
	public String showUpdateAccountPage(Model model) {
		model.addAttribute("user", userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get(0));
		return "UpdateAccount";
	}
	

	@RequestMapping(value="/processUpdateAccount", method=RequestMethod.POST)
	public String processUpdateAccount(User user, Model model) {
		Account account = accountRepo.findByUserId(user.getEmail()).get(0);
		account.setCategory(user.getTempString());
		user.setTempString("");
		UserLog userLog = new UserLog();
		userLog.setCreatedDate(userLogRepo
				.findUserLogByEmail(user.getEmail())
				.get(0)
				.getCreatedDate());
		userLog.setLastPassword(user.getPassword());
		userLog.setEmail(user.getPassword());
		userLog.setLastUpdatedDate(java.time.LocalDateTime.now().toString());
		userLog.setLastUpdatedUserId(
				SecurityContextHolder.getContext().getAuthentication().getName()
		);
		user.setPassword(userRepo.findByEmail(user.getEmail()).get(0).getPassword());
		user.setImage(userRepo.findByEmail(user.getEmail()).get(0).getImage());
		userLogRepo.save(userLog);
		userRepo.save(user);
		accountRepo.save(account);
		CustomEmailService.sendmail("Account Created : "+account.getAccount_number(), account.getUserId(), user.toString());
		return "AccountSummary";
	}	
}
