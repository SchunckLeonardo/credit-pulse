package com.creditpulse.creditpulseapi.repository;

import com.creditpulse.creditpulseapi.entity.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Boolean existsByCpfOrEmail(String cpf, String email);

}
