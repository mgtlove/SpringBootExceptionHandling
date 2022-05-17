package com.cognixia.jump.controller;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidPinException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.repository.CustomerRepository;

@RequestMapping("/api")
@RestController
public class CustomerController {

	@Autowired
	CustomerRepository service;
	
	@GetMapping("/customer")
	public List<Customer> getAllCustomers() {
		
		return service.findAll();
	}
	
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable Long id) {
		
		Optional<Customer> found = service.findById(id);
		
		if(!found.isPresent()) {
			throw new ResolutionException("Customer with id = " + id + " not found");
		}
		
		return found.get();
	}
	
	@PostMapping("/add/customer")
	public void addCustomer(@RequestBody Customer customer) throws InvalidPinException {
		
		customer.setId(-1L);
		
		if(!customer.getPin().matches("[0-9]{4}")) {
			throw new InvalidPinException(customer.getPin());
		}
		
		service.save(customer);
		
	}
	
	@PutMapping("/update/customer")
	public void updateCustomer(@RequestBody Customer customer) throws ResourceNotFoundException {
		
		if (service.existsById(customer.getId()) ) {
			service.save(customer);
		}
		else {
			throw new ResourceNotFoundException("Customer with id = " + customer.getId()
					+ " was not found and could not be updated");
		}
		
	}
	
	@PatchMapping("/update/customer/{id}/account")
	public void updateAccountForCustomer (@RequestBody Account account, @PathVariable Long id) throws ResourceNotFoundException {
		
		Optional<Customer> customerFound = service.findById(id);
		
		if(!customerFound.isPresent()) {
			throw new ResourceNotFoundException("Customer with id = " + id + " not found");
		}
		
		Customer customer = customerFound.get();
		
		if(customer.getAccount(account.getId()).getId() == -1L) {
			throw new ResourceNotFoundException("Account given with id = " + account.getId() + " not found for given customer");
		}
		
		customer.updateAccount(account);
		
		service.save(customer);
	}
	
}
