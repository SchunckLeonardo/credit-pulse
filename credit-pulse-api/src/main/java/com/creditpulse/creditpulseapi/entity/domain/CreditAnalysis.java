package com.creditpulse.creditpulseapi.entity.domain;

import com.creditpulse.creditpulseapi.entity.enums.CreditAnalysisDecisionEnum;
import com.creditpulse.creditpulseapi.entity.enums.RiskLevelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "credit_analyses", indexes = {
        @Index(name = "idx_credit_analyses_proposal_id", columnList = "proposal_id"),
        @Index(name = "idx_credit_analyses_created_at", columnList = "created_at")
})
public class CreditAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposal_id", nullable = false)
    private CreditProposal proposal;

    @NotBlank
    @Size(max = 20)
    @Column(name = "analysis_version", length = 20, nullable = false)
    private String analysisVersion;

    @Column(name = "external_score")
    private Integer externalScore;

    @NotNull
    @Column(name = "internal_score", nullable = false)
    private Integer internalScore;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", length = 20, nullable = false)
    private RiskLevelEnum riskLevel;

    @NotNull
    @DecimalMin("0.00")
    @Column(name = "income_commitment_percentage", precision = 5, scale = 2, nullable = false)
    private BigDecimal incomeCommitmentPercentage;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private CreditAnalysisDecisionEnum decision;

    @NotBlank
    @Size(max = 50)
    @Column(name = "decision_reason_code", length = 50, nullable = false)
    private String decisionReasonCode;

    @NotBlank
    @Size(max = 500)
    @Column(name = "decision_reason_description", length = 500, nullable = false)
    private String decisionReasonDescription;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rules_result", columnDefinition = "jsonb")
    private Map<String, Object> rulesResult = new LinkedHashMap<>();

    @Column(name = "processing_time_ms")
    private Long processingTimeMs;

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

    public String getAnalysisVersion() {
        return analysisVersion;
    }

    public void setAnalysisVersion(String analysisVersion) {
        this.analysisVersion = analysisVersion;
    }

    public Integer getExternalScore() {
        return externalScore;
    }

    public void setExternalScore(Integer externalScore) {
        this.externalScore = externalScore;
    }

    public Integer getInternalScore() {
        return internalScore;
    }

    public void setInternalScore(Integer internalScore) {
        this.internalScore = internalScore;
    }

    public RiskLevelEnum getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevelEnum riskLevel) {
        this.riskLevel = riskLevel;
    }

    public BigDecimal getIncomeCommitmentPercentage() {
        return incomeCommitmentPercentage;
    }

    public void setIncomeCommitmentPercentage(BigDecimal incomeCommitmentPercentage) {
        this.incomeCommitmentPercentage = incomeCommitmentPercentage;
    }

    public CreditAnalysisDecisionEnum getDecision() {
        return decision;
    }

    public void setDecision(CreditAnalysisDecisionEnum decision) {
        this.decision = decision;
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

    public Map<String, Object> getRulesResult() {
        return rulesResult;
    }

    public void setRulesResult(Map<String, Object> rulesResult) {
        this.rulesResult = rulesResult;
    }

    public Long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
