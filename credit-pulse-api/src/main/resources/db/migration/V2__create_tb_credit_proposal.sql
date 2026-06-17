CREATE TABLE tb_credit_proposal
(
    id               SERIAL,
    requested_amount NUMERIC(10, 2),
    installments     INTEGER,
    purpose          VARCHAR(255),
    status           VARCHAR(255),
    score            INTEGER,
    decision_reason  VARCHAR(255),
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    customer_id      UUID,
    CONSTRAINT pk_tb_credit_proposal PRIMARY KEY (id)
);

ALTER TABLE tb_credit_proposal
    ADD CONSTRAINT FK_TB_CREDIT_PROPOSAL_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES tb_customer (id);