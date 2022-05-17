package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.repository.AccountRepository;

@RequestMapping("/api")
@RestController
public class AccountController {

	@Autowired
	AccountRepository service;
	
	@GetMapping("/account")
	public List<Account> getAllAccounts() {
		
		return service.findAll();
	}
	
	@GetMapping("/account/{id}")
	public Account getAccount(@PathVariable long id) throws ResourceNotFoundException {
		
		Optional<Account> found = service.findById(id);
		
		if(!found.isPresent()) {
			throw new ResourceNotFoundException("Account with id = " + id + " was not found");
		}
		
		return found.get();
	}
	
	@GetMapping("/error")
	public void getError() throws Exception {
		throw new Exception("This request returns an error");
	}
}







