package com.creditpulse.creditpulseapi.models.entities;

import com.creditpulse.creditpulseapi.models.enums.CreditProposalStatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_credit_proposal")
public class CreditProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;

    private Integer installments;
    private String purpose;

    @Enumerated(EnumType.STRING)
    private CreditProposalStatusEnum status;

    private Integer score;

    @Column(name = "decision_reason")
    private String decisionReason;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
