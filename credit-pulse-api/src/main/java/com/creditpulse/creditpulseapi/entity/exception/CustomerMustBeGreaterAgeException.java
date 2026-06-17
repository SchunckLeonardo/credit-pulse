package com.creditpulse.creditpulseapi.entity.exception;

public class CustomerMustBeGreaterAgeException extends RuntimeException {
    public CustomerMustBeGreaterAgeException(int yearsOld) {
        super("Customer must be greater than " + yearsOld + " years old");
    }
}
