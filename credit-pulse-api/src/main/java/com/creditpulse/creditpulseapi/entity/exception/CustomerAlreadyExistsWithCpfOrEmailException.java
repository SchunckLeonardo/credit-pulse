package com.creditpulse.creditpulseapi.entity.exception;

public class CustomerAlreadyExistsWithCpfOrEmailException extends RuntimeException {
    private static final String code = "customer.already.exists.with.cpf.or.email";

    public String getCode() {
        return code;
    }

    public CustomerAlreadyExistsWithCpfOrEmailException() {
        super("Customer already exists with CPF or email");
    }
}
