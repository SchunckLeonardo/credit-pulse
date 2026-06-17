package com.creditpulse.creditpulseapi.entity.domain;

import com.creditpulse.creditpulseapi.entity.enums.CalculationMethodEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "credit_simulations", indexes = {
        @Index(name = "idx_credit_simulations_proposal_id", columnList = "proposal_id")
})
public class CreditSimulation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposal_id", nullable = false)
    private CreditProposal proposal;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "approved_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal approvedAmount;

    @NotNull
    @Min(3)
    @Max(60)
    @Column(name = "installment_quantity", nullable = false)
    private Integer installmentQuantity;

    @NotNull
    @DecimalMin("0.000001")
    @Column(name = "monthly_interest_rate", precision = 8, scale = 6, nullable = false)
    private BigDecimal monthlyInterestRate;

    @Column(name = "annual_effective_rate", precision = 8, scale = 4)
    private BigDecimal annualEffectiveRate;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "installment_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal installmentAmount;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "total_interest_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalInterestAmount;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "total_payable_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal totalPayableAmount;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "income_commitment_percentage", precision = 5, scale = 2, nullable = false)
    private BigDecimal incomeCommitmentPercentage;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "calculation_method", length = 30, nullable = false)
    private CalculationMethodEnum calculationMethod;

    @Column(name = "valid_until")
    private Instant validUntil;

    @NotNull
    @Column(nullable = false)
    private Boolean accepted = Boolean.FALSE;

    @Column(name = "accepted_at")
    private Instant acceptedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CreditProposal getProposal() {
        return proposal;
    }

    public void setProposal(CreditProposal proposal) {
        this.proposal = proposal;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public Integer getInstallmentQuantity() {
        return installmentQuantity;
    }

    public void setInstallmentQuantity(Integer installmentQuantity) {
        this.installmentQuantity = installmentQuantity;
    }

    public BigDecimal getMonthlyInterestRate() {
        return monthlyInterestRate;
    }

    public void setMonthlyInterestRate(BigDecimal monthlyInterestRate) {
        this.monthlyInterestRate = monthlyInterestRate;
    }

    public BigDecimal getAnnualEffectiveRate() {
        return annualEffectiveRate;
    }

    public void setAnnualEffectiveRate(BigDecimal annualEffectiveRate) {
        this.annualEffectiveRate = annualEffectiveRate;
    }

    public BigDecimal getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(BigDecimal installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public BigDecimal getTotalInterestAmount() {
        return totalInterestAmount;
    }

    public void setTotalInterestAmount(BigDecimal totalInterestAmount) {
        this.totalInterestAmount = totalInterestAmount;
    }

    public BigDecimal getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(BigDecimal totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public BigDecimal getIncomeCommitmentPercentage() {
        return incomeCommitmentPercentage;
    }

    public void setIncomeCommitmentPercentage(BigDecimal incomeCommitmentPercentage) {
        this.incomeCommitmentPercentage = incomeCommitmentPercentage;
    }

    public CalculationMethodEnum getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(CalculationMethodEnum calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Instant getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Instant acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
