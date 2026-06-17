package com.creditpulse.creditpulseapi.entity.domain;

import com.creditpulse.creditpulseapi.entity.enums.ChangedByTypeEnum;
import com.creditpulse.creditpulseapi.entity.enums.CreditProposalStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "proposal_status_history", indexes = {
        @Index(name = "idx_proposal_status_history_proposal_id", columnList = "proposal_id"),
        @Index(name = "idx_proposal_status_history_created_at", columnList = "created_at")
})
public class ProposalStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposal_id", nullable = false)
    private CreditProposal proposal;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", length = 40)
    private CreditProposalStatusEnum previousStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", length = 40, nullable = false)
    private CreditProposalStatusEnum newStatus;

    @Size(max = 50)
    @Column(name = "reason_code", length = 50)
    private String reasonCode;

    @Size(max = 500)
    @Column(name = "reason_description", length = 500)
    private String reasonDescription;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "changed_by_type", length = 20, nullable = false)
    private ChangedByTypeEnum changedByType;

    @Column(name = "changed_by_id")
    private UUID changedById;

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

    public CreditProposalStatusEnum getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(CreditProposalStatusEnum previousStatus) {
        this.previousStatus = previousStatus;
    }

    public CreditProposalStatusEnum getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(CreditProposalStatusEnum newStatus) {
        this.newStatus = newStatus;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
    }

    public ChangedByTypeEnum getChangedByType() {
        return changedByType;
    }

    public void setChangedByType(ChangedByTypeEnum changedByType) {
        this.changedByType = changedByType;
    }

    public UUID getChangedById() {
        return changedById;
    }

    public void setChangedById(UUID changedById) {
        this.changedById = changedById;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}
