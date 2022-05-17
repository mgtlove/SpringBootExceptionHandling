package com.cognixia.jump.exception;

public class InvalidPinException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidPinException(String pin) {
		super("Pin given is invalid, pin should be four digits exactly, given pin is " + pin);
	}

	
}
