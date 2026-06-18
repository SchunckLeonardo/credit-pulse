package com.creditpulse.creditpulseapi.entity.domain;

import com.creditpulse.creditpulseapi.entity.enums.CustomerStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers", uniqueConstraints = {
        @UniqueConstraint(name = "uk_customers_cpf", columnNames = "cpf"),
        @UniqueConstraint(name = "uk_customers_email", columnNames = "email")
}, indexes = {
        @Index(name = "idx_customers_status", columnList = "status"),
        @Index(name = "idx_customers_created_at", columnList = "created_at")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", length = 150, nullable = false)
    private String fullName;

    @Column(length = 11, nullable = false)
    private String cpf;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(length = 150, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(name = "monthly_income", precision = 15, scale = 2, nullable = false)
    private BigDecimal monthlyIncome;

    @Column(length = 100, nullable = false)
    private String profession;

    @Column(name = "employment_type", length = 30)
    private String employmentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CustomerStatusEnum status;

    @Size(max = 255)
    @Column(name = "blocked_reason", length = 255)
    private String blockedReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<CreditProposal> creditProposals = new ArrayList<>();

    public Customer(String fullName, String cpf, LocalDate birthDate, String email, String phone, BigDecimal monthlyIncome, String profession, String employmentType, CustomerStatusEnum status) {
        this.fullName = fullName;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.monthlyIncome = monthlyIncome;
        this.profession = profession;
        this.employmentType = employmentType;
        this.status = status;
    }

    public Customer() {

    }

    @PrePersist
    void prePersist() {
        var now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public CustomerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CustomerStatusEnum status) {
        this.status = status;
    }

    public String getBlockedReason() {
        return blockedReason;
    }

    public void setBlockedReason(String blockedReason) {
        this.blockedReason = blockedReason;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<CustomerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<CustomerAddress> addresses) {
        this.addresses = addresses;
    }

    public List<CreditProposal> getCreditProposals() {
        return creditProposals;
    }

    public void setCreditProposals(List<CreditProposal> creditProposals) {
        this.creditProposals = creditProposals;
    }

    @AssertTrue(message = "Customer must be at least 18 years old")
    public boolean isAdult() {
        return birthDate != null && !birthDate.isAfter(LocalDate.now().minusYears(18));
    }

}
