package com.creditpulse.creditpulseapi.entity.domain;

import com.creditpulse.creditpulseapi.entity.enums.ManualAnalysisDecisionEnum;
import com.creditpulse.creditpulseapi.entity.enums.ManualAnalysisStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "manual_analyses", indexes = {
        @Index(name = "idx_manual_analyses_proposal_id", columnList = "proposal_id"),
        @Index(name = "idx_manual_analyses_status", columnList = "status")
})
public class ManualAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposal_id", nullable = false)
    private CreditProposal proposal;

    @Column(name = "analyst_id")
    private UUID analystId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private ManualAnalysisStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ManualAnalysisDecisionEnum decision;

    @Size(max = 1000)
    @Column(length = 1000)
    private String reason;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "finished_at")
    private Instant finishedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

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

    public CreditProposal getProposal() {
        return proposal;
    }

    public void setProposal(CreditProposal proposal) {
        this.proposal = proposal;
    }

    public UUID getAnalystId() {
        return analystId;
    }

    public void setAnalystId(UUID analystId) {
        this.analystId = analystId;
    }

    public ManualAnalysisStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ManualAnalysisStatusEnum status) {
        this.status = status;
    }

    public ManualAnalysisDecisionEnum getDecision() {
        return decision;
    }

    public void setDecision(ManualAnalysisDecisionEnum decision) {
        this.decision = decision;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
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

}
