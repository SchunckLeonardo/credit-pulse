package com.creditpulse.creditpulseapi.service;

import com.creditpulse.creditpulseapi.entity.dto.CustomerDTO;
import com.creditpulse.creditpulseapi.entity.dto.request.CreateCustomerRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.request.GetAllCustomerFilterRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.request.UpdateCustomerRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.response.GetCustomerResponseDTO;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerDTO createCustomer(CreateCustomerRequestDTO dto);
    GetCustomerResponseDTO getCustomerById(String id);
    GetCustomerResponseDTO getCustomerByCpf(String cpf);
    void updateCustomer(String id, UpdateCustomerRequestDTO dto);
    void blockCustomer(String id);
    void unblockCustomer(String id);
    void deleteCustomer(String id);
    Page<GetCustomerResponseDTO> getAllCustomers(GetAllCustomerFilterRequestDTO dto);

}
