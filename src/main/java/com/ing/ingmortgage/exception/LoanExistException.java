package com.ing.ingmortgage.exception;

public class LoanExistException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public LoanExistException(String message) {
		super(message);
	}
}
