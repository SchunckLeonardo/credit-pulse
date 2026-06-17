package com.creditpulse.creditpulseapi.entity.exception;

public class CustomerAlreadyExistsWithCpfOrEmailException extends RuntimeException {
    public CustomerAlreadyExistsWithCpfOrEmailException() {
        super("Customer already exists with CPF or email");
    }
}
