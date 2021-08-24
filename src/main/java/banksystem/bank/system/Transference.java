/**
Author - Pedro de Oliveira Lima Nunes
*/

package banksystem.bank.system;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transference {
	private String source_account;
	private String destiny_account;
	private float ammount;
	private @Id @GeneratedValue Long id;

	public Transference() {
	}

	Transference(String source_account, String destiny_account, float ammount) {

		this.source_account = source_account;
		this.destiny_account = destiny_account;
		this.ammount = ammount;
	}

	public String getSource_account() {
		return source_account;
	}

	public void setSource_account(String source_account) {
		this.source_account = source_account;
	}

	public String getDestiny_account() {
		return destiny_account;
	}

	public void setDestiny_account(String destiny_account) {
		this.destiny_account = destiny_account;
	}

	public float getAmmount() {
		return ammount;
	}

	public void setAmmount(int ammount) {
		this.ammount = ammount;
	}

}