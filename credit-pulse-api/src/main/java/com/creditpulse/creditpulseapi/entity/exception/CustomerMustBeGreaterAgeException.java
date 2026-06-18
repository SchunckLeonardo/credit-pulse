package com.creditpulse.creditpulseapi.entity.exception;

public class CustomerMustBeGreaterAgeException extends RuntimeException {
    private static final String code = "customer.must.be.greater.age";

    public String getCode() {
        return code;
    }

    public CustomerMustBeGreaterAgeException(int yearsOld) {
        super("Customer must be greater than " + yearsOld + " years old");
    }
}
