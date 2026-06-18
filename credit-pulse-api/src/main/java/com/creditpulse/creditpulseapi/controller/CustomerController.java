package com.creditpulse.creditpulseapi.controller;

import com.creditpulse.creditpulseapi.entity.dto.request.CreateCustomerRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.response.GetCustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CustomerController {

    ResponseEntity<Void> createCustomer(CreateCustomerRequestDTO body);
    ResponseEntity<Page<GetCustomerResponseDTO>> getAllCustomers(int page, int size);

}
