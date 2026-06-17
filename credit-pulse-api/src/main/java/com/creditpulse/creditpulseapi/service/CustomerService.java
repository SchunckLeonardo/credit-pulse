package com.creditpulse.creditpulseapi.service;

import com.creditpulse.creditpulseapi.entity.dto.request.CreateCustomerRequestDTO;

public interface CustomerService {

    void createCustomer(CreateCustomerRequestDTO body);

}
