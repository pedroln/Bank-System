package banksystem.bank.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

  private @Id @GeneratedValue Long id;	
  private String number;
  private String balance;
  
  
  public Account() {  
  }  
  
  Account(String number, String balance, User user) {
	  	
	    this.number = number;
	    this.balance = balance;
	    
	  }

public String getNumber() {
	return number;
}

public void setNumber(String number) {
	this.number = number;
}

public String getBalance() {
	return balance;
}

public void setBalance(String balance) {
	this.balance = balance;
}
}