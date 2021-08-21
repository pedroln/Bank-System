package banksystem.bank.system;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

  private @Id @GeneratedValue Long id;	
  private String email;
  private String name;
  private String password;
  private String token;
  
  public User() {  
  }  
  
  User(String email, String name, String password) {
	  	
	    this.email = email;
	    this.name = name;
	    this.password = password;
	  }
  
  
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

public String toString() {
    return "{\n" + " email=" + this.email + ",\n name=" + this.name  +  "\n" + '}';
  }

@Override
public boolean equals(Object o) {

  if (this == o)
    return true;
  if (!(o instanceof User))
    return false;
  User User = (User) o;
  return Objects.equals(this.id, User.id) && Objects.equals(this.name, User.name)
      && Objects.equals(this.password, User.password);
}

@Override
public int hashCode() {
  return Objects.hash(this.id, this.name, this.password);
}

public void setToken(String token) {
	this.token = token;
	
}
 
public String getToken() {
	return token;
}
}