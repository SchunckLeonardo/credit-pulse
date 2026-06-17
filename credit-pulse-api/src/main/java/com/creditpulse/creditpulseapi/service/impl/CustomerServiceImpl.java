package com.creditpulse.creditpulseapi.service.impl;

import com.creditpulse.creditpulseapi.entity.domain.Customer;
import com.creditpulse.creditpulseapi.entity.dto.request.CreateCustomerRequestDTO;
import com.creditpulse.creditpulseapi.entity.exception.CustomerAlreadyExistsWithCpfOrEmailException;
import com.creditpulse.creditpulseapi.entity.exception.CustomerMustBeGreaterAgeException;
import com.creditpulse.creditpulseapi.repository.CustomerRepository;
import com.creditpulse.creditpulseapi.service.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private static final int MIN_AGE = 18;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void createCustomer(CreateCustomerRequestDTO body) {
        var doesCustomerAlreadyExistsWithCpf = customerRepository.existsByCpfOrEmail(body.cpf(), body.email());

        if (doesCustomerAlreadyExistsWithCpf) {
            throw new CustomerAlreadyExistsWithCpfOrEmailException();
        }

        var doesCustomerIsGreaterAge = LocalDate.now().minusYears(MIN_AGE).isBefore(body.birthDate());

        if (doesCustomerIsGreaterAge) {
            throw new CustomerMustBeGreaterAgeException(MIN_AGE);
        }

        var customer = new Customer(
                body.fullName(),
                body.cpf(),
                body.birthDate(),
                body.email(),
                body.phone(),
                body.monthlyIncome(),
                body.profession(),
                body.employmentType()
        );

        customerRepository.save(customer);
    }
}
