package com.creditpulse.creditpulseapi.entity.domain;

import com.creditpulse.creditpulseapi.entity.enums.CustomerStatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_customer", indexes = {
        @Index(name = "idx_customer_cpf", columnList = "cpf", unique = true),
        @Index(name = "idx_customer_email", columnList = "email", unique = true),
        @Index(name = "idx_customer_status", columnList = "status"),
        @Index(name = "idx_customer_created_at", columnList = "created_at")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @Column(length = 150, nullable = false)
    private String name;

    @Column(unique = true, length = 11, nullable = false)
    private String cpf;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "monthly_income", scale = 15, precision = 2, nullable = false)
    private BigDecimal monthlyIncome;

    @Column(length = 100, nullable = false)
    private String profession;

    @Column(name = "employment_type", length = 30)
    private String employmentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatusEnum status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer")
    private List<CreditProposal> creditProposals;

}
