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
import banksystem.bank.system.Transference;
import banksystem.bank.system.exceptions.AccountNotFoundException;
import banksystem.bank.system.exceptions.AccountNotLoggedException;
import banksystem.bank.system.exceptions.DestinyAccountNotFoundException;
import banksystem.bank.system.exceptions.FieldNotOnBodyException;
import banksystem.bank.system.exceptions.InsuficientBalanceException;
import banksystem.bank.system.exceptions.InvalidAmmountException;
import banksystem.bank.system.exceptions.SourceAccountNotFoundException;
import banksystem.bank.system.exceptions.TokenNotMatchingException;

@RestController
public class TransferController {
	
	  private final LoginRepository loginRepository;
	  private final AccountRepository accountRepository;
	  
	  TransferController(LoginRepository loginRepository, AccountRepository accountRepository) {
	    
	    this.loginRepository = loginRepository;
	    this.accountRepository = accountRepository;
	        
	  }
	  
	  

	  @PostMapping("accounts/transfer")
	  ResponseEntity<Object> newAccount(@RequestHeader("Authorization") String token, @RequestBody Transference transference) {
		
		  List<LoggedUser> loggedUsers = loginRepository.findAll();
		  List<Account> registeredAccounts = accountRepository.findAll();
		  HashMap<String, String> responseMessage = new HashMap<>(); 
		  String transferAmmount = String.valueOf(transference.getAmmount());
		  boolean foundAccount = false;
		  boolean sourceAccount = false;
		  boolean destinyAccount = false;
		  boolean loggedAccount = false;
		  float sourceAccountBalance = 0;
		  float destinyAccountBalance = 0;
		  String user_email = null;
		  String user_name = null;
		  String sourceAccountEmail = null;
		  

		  if(loggedUsers.isEmpty()) {
			  throw new TokenNotMatchingException();
		  	}
		  
		  if (transference.getDestiny_account() == null || transference.getSource_account() == null) {
			  throw new FieldNotOnBodyException();
		  }
		  
		  for (LoggedUser loggedUser : loggedUsers) {

			  if (loggedUser.getToken().equals(token)){
				  foundAccount = true;
				  user_email = loggedUser.getEmail();
				  user_name = loggedUser.getName();
				  
				  
			  }
		  }
		  
		  
		  if (foundAccount == true) {
			  
			  if (registeredAccounts.isEmpty()) {
				  throw new AccountNotFoundException();
			  }
			  else {
			      
				  for (Account chosenAccount : registeredAccounts ) {
					
					  if (chosenAccount.getNumber().equals(transference.getSource_account())) {	
						  sourceAccount = true;
						  sourceAccountBalance = chosenAccount.getBalance();
						  sourceAccountEmail = chosenAccount.getEmail();

					  }
				  }
				  
				  if (user_email.equals(sourceAccountEmail)) {
					  loggedAccount = true;
				  }
				  
				  else {
					  new AccountNotLoggedException();
				  }
				  
				  
				  if (sourceAccount == true && loggedAccount == true) {
					  for (Account chosenAccount : registeredAccounts) {
						  if (chosenAccount.getNumber().equals(transference.getDestiny_account())) {
							  destinyAccount = true;
							  destinyAccountBalance = chosenAccount.getBalance();
						  }
					  }  
				  }
				  
				  else {
					  throw new SourceAccountNotFoundException();
				  }
				  
				  if (destinyAccount == true) {
					  float ammount = transference.getAmmount();
					  
					  if (ammount < 0.0) {
						  throw new InvalidAmmountException();
						  
					  }
					  
					  if ( sourceAccountBalance - ammount < 0) {
						  throw new InsuficientBalanceException();
						  
					  }
						  
					  else {
						  for (Account chosenAccount : registeredAccounts) {
							  if (chosenAccount.getNumber().equals(transference.getSource_account())) {	
								  chosenAccount.setBalance(sourceAccountBalance - ammount);
								  accountRepository.save(chosenAccount);
							  }
							  if (chosenAccount.getNumber().equals(transference.getDestiny_account())) {
								  chosenAccount.setBalance(destinyAccountBalance + ammount);
								  accountRepository.save(chosenAccount);
							  }
						  }
						  
						 responseMessage.put("ammount", transferAmmount);
						 responseMessage.put("source_account_number", transference.getSource_account());
						 responseMessage.put("destiny_account_number", transference.getDestiny_account());
						 responseMessage.put("email_transfer", user_email);
						 responseMessage.put("name_transfer", user_name);
						 
					  }
				  }
				  
				  else {
					  throw new DestinyAccountNotFoundException();
				  }
	  
			  }
		  }
	  
		  else {
			  throw new TokenNotMatchingException();
		  }
		  
		  

		  return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		  	
		
	  }
}