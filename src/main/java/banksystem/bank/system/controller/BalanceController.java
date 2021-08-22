package banksystem.bank.system.controller;

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
import banksystem.bank.system.exceptions.AccountNotFoundException;
import banksystem.bank.system.exceptions.FieldNotOnBodyException;
import banksystem.bank.system.exceptions.TokenNotMatchingException;

@RestController
public class BalanceController {
	
	private final LoginRepository loginRepository;
	private final AccountRepository accountRepository;
	  
	 BalanceController(LoginRepository loginRepository, AccountRepository accountRepository) {
	    
	    this.loginRepository = loginRepository;
	    this.accountRepository = accountRepository;
	        
	  }
	 
	 @PostMapping("accounts/balance")
	  ResponseEntity<Object> checkBalance(@RequestHeader("Authorization") String token, @RequestBody Account selectedAccount) {
		
		  List<LoggedUser> loggedUsers = loginRepository.findAll();
		  List<Account> registeredAccounts = accountRepository.findAll();
		  boolean foundAccount = false;
		  boolean foundAccountBalance = false;
		  Account returnAccount = new Account ();
		  
		  
		  
		  if(loggedUsers.isEmpty()) {
			  throw new TokenNotMatchingException();
		  	}
		  
		  if (selectedAccount.getNumber() == null) {
			  throw new FieldNotOnBodyException();
		  }
		  
		  for (LoggedUser loggedUser : loggedUsers) {

			  if (loggedUser.getToken().equals(token)){
				  foundAccount = true;  
 
			  }
			  
			  else {
				  throw new TokenNotMatchingException();
			  }
		  }
		  
		  if(foundAccount == true) {
		  
					  if (registeredAccounts.isEmpty()) {
						  throw new AccountNotFoundException();
					  }
					  else {
						  for (Account accounts : registeredAccounts) {
							 if (accounts.getNumber().equals(selectedAccount.getNumber())) {
								 foundAccountBalance = true;
								 returnAccount = accounts;
							 }
						  }
					  }	  
					  
					  if (foundAccountBalance == true){
						  return new ResponseEntity<>(returnAccount, HttpStatus.OK);
					  }
					  else {
						  throw new AccountNotFoundException();
					  }
				  
			  
		  }
		  
		  throw new AccountNotFoundException();

	 }
}