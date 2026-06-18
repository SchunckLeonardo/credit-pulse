package com.creditpulse.creditpulseapi.controller.impl;

import com.creditpulse.creditpulseapi.controller.CustomerController;
import com.creditpulse.creditpulseapi.entity.dto.request.CreateCustomerRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.request.GetAllCustomerFilterRequestDTO;
import com.creditpulse.creditpulseapi.entity.dto.response.GetCustomerResponseDTO;
import com.creditpulse.creditpulseapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/customers")
public class CustomerControllerImpl implements CustomerController {

    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> createCustomer(
            @RequestBody @Valid CreateCustomerRequestDTO body
    ) {
        var customer = customerService.createCustomer(body);
        var uri = URI.create(String.format("/v1/customers/%s", customer.id()));
        return ResponseEntity.created(uri).build();
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<GetCustomerResponseDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(customerService.getAllCustomers(
                new GetAllCustomerFilterRequestDTO(page, size)
        ));
    }


}
