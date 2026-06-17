package com.creditpulse.creditpulseapi.models.entities;

import com.creditpulse.creditpulseapi.models.enums.CustomerStatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    private String name;

    @Column(unique = true, length = 11)
    private String cpf;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "monthly_income")
    private BigDecimal monthlyIncome;

    @Column(nullable = true)
    private String profession;

    @Enumerated(EnumType.STRING)
    private CustomerStatusEnum status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
