/**
Author - Pedro de Oliveira Lima Nunes
*/

package banksystem.bank.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LoggedUser {
	private String email;
	private String name;
	private String token;
	private @Id @GeneratedValue Long id;

	public LoggedUser() {
	}

	LoggedUser(String email, String name, String token) {

		this.email = email;
		this.name = name;
		this.token = token;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
