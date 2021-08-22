package banksystem.bank.system.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import banksystem.bank.system.Account;
import banksystem.bank.system.AccountRepository;
import banksystem.bank.system.LoggedUser;
import banksystem.bank.system.LoginRepository;
import banksystem.bank.system.User;
import banksystem.bank.system.exceptions.ExistingAccountNumberException;
import banksystem.bank.system.exceptions.FieldNotOnBodyException;
import banksystem.bank.system.exceptions.InvalidBalanceException;
import banksystem.bank.system.exceptions.TokenNotMatchingException;

	@RestController
	public class AccountController {
		
		User user = new User();

		  
		  private final LoginRepository loginRepository;
		  private final AccountRepository accountRepository;
		  
		  AccountController(LoginRepository loginRepository, AccountRepository accountRepository) {
		    
		    this.loginRepository = loginRepository;
		    this.accountRepository = accountRepository;
		        
		  }
		  
		  
		  // falta tratamento para aceitar so os campos do account
		  @PostMapping("accounts")
		  ResponseEntity<Object> newUser(@RequestHeader("Authorization") String token, @RequestBody Account newAccount) {
			
			  List<LoggedUser> loggedUsers = loginRepository.findAll();
			  List<Account> registeredAccounts = accountRepository.findAll();
			  HashMap<String, String> responseMessage = new HashMap<>(); 
			  String balance = String.valueOf(newAccount.getBalance());
			  
			  
			
			  if(loggedUsers.isEmpty()) {
				  throw new TokenNotMatchingException();
			  	}
			  
			  if (newAccount.getNumber() == null) {
				  throw new FieldNotOnBodyException();
			  }
			  
			  for (LoggedUser loggedUser : loggedUsers) {
				  
				 
				  if (loggedUser.getToken().equals(token)){
					  
					  if (registeredAccounts.isEmpty()) {
						    
						  if (newAccount.getBalance() < 0.0) {
					
							  throw new InvalidBalanceException();
						  }
						  
						  
						  responseMessage.put("number", newAccount.getNumber());
						  responseMessage.put("balance", balance);
						  responseMessage.put("email", loggedUser.getEmail());
						  responseMessage.put("name", loggedUser.getName());
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
								  responseMessage.put("email", loggedUser.getEmail());
								  responseMessage.put("name", loggedUser.getName());
								  accountRepository.save(newAccount);
							  }
							
						  
					  }
				  }
				  else {
					  throw new TokenNotMatchingException();
				  }
						  	
			  }
			  return new ResponseEntity<>(responseMessage, HttpStatus.OK);
			  	}
		  
	
			
	}

