package banksystem.bank.system.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import banksystem.bank.system.Account;
import banksystem.bank.system.AccountRepository;
import banksystem.bank.system.LoggedUser;
import banksystem.bank.system.LoginRepository;
import banksystem.bank.system.exceptions.ExistingAccountNumberException;
import banksystem.bank.system.exceptions.FieldNotOnBodyException;
import banksystem.bank.system.exceptions.InvalidBalanceException;
import banksystem.bank.system.exceptions.TokenNotMatchingException;

	@RestController
	public class AccountController {
		
		  private final LoginRepository loginRepository;
		  private final AccountRepository accountRepository;
		  
		  AccountController(LoginRepository loginRepository, AccountRepository accountRepository) {
		    
		    this.loginRepository = loginRepository;
		    this.accountRepository = accountRepository;
		        
		  }
		  
		  
		  @GetMapping("/accountlist")
			 List<Account> all() {
			    return accountRepository.findAll();
			  }  
	
		  @PostMapping("accounts")
		  ResponseEntity<Object> newAccount(@RequestHeader("Authorization") String token, @RequestBody Account newAccount) {
			
			  List<LoggedUser> loggedUsers = loginRepository.findAll();
			  List<Account> registeredAccounts = accountRepository.findAll();
			  HashMap<String, String> responseMessage = new HashMap<>(); 
			  String balance = String.valueOf(newAccount.getBalance());
			  boolean foundAccount = false;
			  String email = null;
			  String name = null;
			  
			  
			  
			  
			
			  if(loggedUsers.isEmpty()) {
				  throw new TokenNotMatchingException();
			  	}
			  
			  if (newAccount.getNumber() == null) {
				  throw new FieldNotOnBodyException();
			  }
			  
			  for (LoggedUser loggedUser : loggedUsers) {

				  if (loggedUser.getToken().equals(token)){
					  foundAccount = true;  
					  email = loggedUser.getEmail();
					  name = loggedUser.getName();
				  }
			  }
			  
			  if (foundAccount == true) {
				  
				  if (registeredAccounts.isEmpty()) {
					  if (newAccount.getBalance() < 0.0) {
				
						  throw new InvalidBalanceException();
					  }
					  
					  
					  responseMessage.put("number", newAccount.getNumber());
					  responseMessage.put("balance", balance);
					  responseMessage.put("email", email);
					  responseMessage.put("name", name);
					  accountRepository.save(newAccount);
					  
				  }
				  else {
				      
					  for (Account chosenAccount : registeredAccounts ) {
						
						  if (chosenAccount.getNumber().equals(newAccount.getNumber())) {	
							  
							  throw new ExistingAccountNumberException();
						  }
					  }
						  
						  if (newAccount.getBalance() < 0.0) {
							  
							  throw new InvalidBalanceException();
						  }
						  
						  else {
							  responseMessage.put("number", newAccount.getNumber());
							  responseMessage.put("balance", balance);
							  responseMessage.put("email", email);
							  responseMessage.put("name", name);
							  accountRepository.save(newAccount);
						  }
				  }
			  }
			  
			  else {
				  throw new TokenNotMatchingException();
			  }

			  return new ResponseEntity<>(responseMessage, HttpStatus.OK);
			  	
			
		  }
	}
				  

