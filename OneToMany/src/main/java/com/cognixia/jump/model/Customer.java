package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@Column(columnDefinition = "char(4) default '####'")
	private String pin;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<Account> accounts;

	public Customer() {
		this(-1L, "N/A", "N/A", "####", new ArrayList<Account>());
	}
	
	public Customer(Long id, @NotBlank String firstName, @NotBlank String lastName, String pin,
			List<Account> accounts) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pin = pin;
		this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		// this will override all your accounts
		//this.accounts = accounts;
		
		for(int acc = 0; acc < accounts.size(); acc++) {
			addAccount(accounts.get(acc));
		}
	}
	
	public void addAccount(Account account) {
		account.setCustomer(this);
		accounts.add(account);
	}
	
	public Account getAccount(Long id) {
		
		for(int a = 0; a < accounts.size(); a++) {
			
			if(accounts.get(a).getId() == id) {
				return accounts.get(a);
			}
		}
		
		return new Account();
	}
	
	public void updateAccount(Account account) {
		
		Account toUpdate = getAccount(account.getId());
		
		if(toUpdate.getId() != -1) {
			
			toUpdate.setBalance(account.getBalance());
			toUpdate.setType(account.getType());
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", pin=" + pin + "]";
	}
	
}








