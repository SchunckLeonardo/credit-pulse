package com.creditpulse.creditpulseapi.service.impl;

import com.creditpulse.creditpulseapi.entity.domain.Customer;
import com.creditpulse.creditpulseapi.entity.dto.CustomerDTO;
import com.creditpulse.creditpulseapi.entity.dto.request.CreateCustomerRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.request.GetAllCustomerFilterRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.request.UpdateCustomerRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.response.GetCustomerResponseDTO;
import com.creditpulse.creditpulseapi.entity.enums.CustomerStatusEnum;
import com.creditpulse.creditpulseapi.entity.exception.CustomerAlreadyExistsWithCpfOrEmailException;
import com.creditpulse.creditpulseapi.entity.exception.CustomerMustBeGreaterAgeException;
import com.creditpulse.creditpulseapi.repository.CustomerRepository;
import com.creditpulse.creditpulseapi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public CustomerDTO createCustomer(CreateCustomerRequestDTO dto) {
        var doesCustomerAlreadyExistsWithCpf = customerRepository.existsByCpfOrEmail(dto.cpf(), dto.email());

        if (doesCustomerAlreadyExistsWithCpf) {
            throw new CustomerAlreadyExistsWithCpfOrEmailException();
        }

        var customerIsUnderAge = dto.birthDate()
                .plusYears(MIN_AGE)
                .isAfter(LocalDate.now());

        if (customerIsUnderAge) {
            throw new CustomerMustBeGreaterAgeException(MIN_AGE);
        }

        var customer = new Customer(
                dto.fullName(),
                dto.cpf(),
                dto.birthDate(),
                dto.email(),
                dto.phone(),
                dto.monthlyIncome(),
                dto.profession(),
                dto.employmentType(),
                CustomerStatusEnum.ACTIVE
        );

        customerRepository.save(customer);

        return new CustomerDTO(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getCpf(),
                customer.getPhone(),
                customer.getBirthDate(),
                customer.getMonthlyIncome(),
                customer.getProfession(),
                customer.getEmploymentType()
        );
    }

    @Override
    public GetCustomerResponseDTO getCustomerById(String id) {
        return null;
    }

    @Override
    public GetCustomerResponseDTO getCustomerByCpf(String cpf) {
        return null;
    }

    @Override
    public void updateCustomer(String id, UpdateCustomerRequestDTO dto) {

    }

    @Override
    public void blockCustomer(String id) {

    }

    @Override
    public void unblockCustomer(String id) {

    }

    @Override
    public void deleteCustomer(String id) {

    }

    @Override
    public Page<GetCustomerResponseDTO> getAllCustomers(GetAllCustomerFilterRequestDTO dto) {
        Pageable pageable = PageRequest.of(dto.page(), dto.size());
        return customerRepository.findAll(pageable).map(customer -> new GetCustomerResponseDTO(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getCpf(),
                customer.getPhone(),
                customer.getBirthDate().toString(),
                customer.getMonthlyIncome(),
                customer.getProfession(),
                customer.getEmploymentType(),
                customer.getStatus().name(),
                customer.getBlockedReason(),
                customer.getCreatedAt().toString(),
                customer.getUpdatedAt().toString()
        ));
    }
}
