package banksystem.bank.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

  private @Id @GeneratedValue long id;	
  private String number;
  private float balance;
  private String email;
  
  


public Account() {  
  }  
  
  Account(String number, float balance, User user, String email) {
	  	
	    this.number = number;
	    this.balance = balance;
	    this.email = email;
	    
	  }

public String getNumber() {
	return number;
}

public void setNumber(String number) {
	this.number = number;
}

public float getBalance() {
	return balance;
}

public void setBalance(float balance) {
	this.balance = balance;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}



}