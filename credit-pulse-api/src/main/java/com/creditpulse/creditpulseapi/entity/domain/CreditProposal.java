package com.creditpulse.creditpulseapi.entity.domain;

import com.creditpulse.creditpulseapi.entity.enums.CreditPurposeEnum;
import com.creditpulse.creditpulseapi.entity.enums.CreditProposalStatusEnum;
import com.creditpulse.creditpulseapi.entity.enums.RiskLevelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit_proposals", indexes = {
        @Index(name = "idx_credit_proposals_customer_id", columnList = "customer_id"),
        @Index(name = "idx_credit_proposals_status", columnList = "status"),
        @Index(name = "idx_credit_proposals_created_at", columnList = "created_at"),
        @Index(name = "idx_credit_proposals_customer_status", columnList = "customer_id,status"),
        @Index(name = "idx_credit_proposals_current_score", columnList = "current_score"),
        @Index(name = "idx_credit_proposals_risk_level", columnList = "risk_level")
})
public class CreditProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @DecimalMin("500.00")
    @DecimalMax("100000.00")
    @Column(name = "requested_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal requestedAmount;

    @NotNull
    @Min(3)
    @Max(60)
    @Column(name = "installment_quantity", nullable = false)
    private Integer installmentQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private CreditPurposeEnum purpose;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 40, nullable = false)
    private CreditProposalStatusEnum status;

    @Column(name = "current_score")
    private Integer currentScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", length = 20)
    private RiskLevelEnum riskLevel;

    @Size(max = 50)
    @Column(name = "decision_reason_code", length = 50)
    private String decisionReasonCode;

    @Size(max = 500)
    @Column(name = "decision_reason_description", length = 500)
    private String decisionReasonDescription;

    @Column(name = "approved_amount", precision = 15, scale = 2)
    private BigDecimal approvedAmount;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "analyzed_at")
    private Instant analyzedAt;

    @Column(name = "cancelled_at")
    private Instant cancelledAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "proposal")
    private List<CreditAnalysis> analyses = new ArrayList<>();

    @OneToMany(mappedBy = "proposal")
    private List<CreditSimulation> simulations = new ArrayList<>();

    @OneToMany(mappedBy = "proposal")
    private List<ManualAnalysis> manualAnalyses = new ArrayList<>();

    @OneToMany(mappedBy = "proposal")
    private List<ProposalStatusHistory> statusHistory = new ArrayList<>();

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getInstallmentQuantity() {
        return installmentQuantity;
    }

    public void setInstallmentQuantity(Integer installmentQuantity) {
        this.installmentQuantity = installmentQuantity;
    }

    public CreditPurposeEnum getPurpose() {
        return purpose;
    }

    public void setPurpose(CreditPurposeEnum purpose) {
        this.purpose = purpose;
    }

    public CreditProposalStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CreditProposalStatusEnum status) {
        this.status = status;
    }

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public RiskLevelEnum getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevelEnum riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getDecisionReasonCode() {
        return decisionReasonCode;
    }

    public void setDecisionReasonCode(String decisionReasonCode) {
        this.decisionReasonCode = decisionReasonCode;
    }

    public String getDecisionReasonDescription() {
        return decisionReasonDescription;
    }

    public void setDecisionReasonDescription(String decisionReasonDescription) {
        this.decisionReasonDescription = decisionReasonDescription;
    }

    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Instant getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(Instant analyzedAt) {
        this.analyzedAt = analyzedAt;
    }

    public Instant getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Instant cancelledAt) {
        this.cancelledAt = cancelledAt;
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

    public List<CreditAnalysis> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(List<CreditAnalysis> analyses) {
        this.analyses = analyses;
    }

    public List<CreditSimulation> getSimulations() {
        return simulations;
    }

    public void setSimulations(List<CreditSimulation> simulations) {
        this.simulations = simulations;
    }

    public List<ManualAnalysis> getManualAnalyses() {
        return manualAnalyses;
    }

    public void setManualAnalyses(List<ManualAnalysis> manualAnalyses) {
        this.manualAnalyses = manualAnalyses;
    }

    public List<ProposalStatusHistory> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<ProposalStatusHistory> statusHistory) {
        this.statusHistory = statusHistory;
    }

}
