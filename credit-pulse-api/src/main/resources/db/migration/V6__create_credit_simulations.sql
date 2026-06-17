CREATE TABLE credit_simulations
(
    id                           UUID                     NOT NULL DEFAULT gen_random_uuid(),
    proposal_id                  UUID                     NOT NULL,
    approved_amount              NUMERIC(15, 2)           NOT NULL,
    installment_quantity         INTEGER                  NOT NULL,
    monthly_interest_rate        NUMERIC(8, 6)            NOT NULL,
    annual_effective_rate        NUMERIC(8, 4),
    installment_amount           NUMERIC(15, 2)           NOT NULL,
    total_interest_amount        NUMERIC(15, 2)           NOT NULL,
    total_payable_amount         NUMERIC(15, 2)           NOT NULL,
    income_commitment_percentage NUMERIC(5, 2)            NOT NULL,
    calculation_method           VARCHAR(30)              NOT NULL,
    valid_until                  TIMESTAMP WITH TIME ZONE,
    accepted                     BOOLEAN                  NOT NULL DEFAULT FALSE,
    accepted_at                  TIMESTAMP WITH TIME ZONE,
    created_at                   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_credit_simulations PRIMARY KEY (id),
    CONSTRAINT fk_credit_simulations_proposal FOREIGN KEY (proposal_id) REFERENCES credit_proposals (id),
    CONSTRAINT ck_credit_simulations_approved_amount CHECK (approved_amount > 0),
    CONSTRAINT ck_credit_simulations_installment_quantity CHECK (installment_quantity BETWEEN 3 AND 60),
    CONSTRAINT ck_credit_simulations_monthly_interest_rate CHECK (monthly_interest_rate > 0),
    CONSTRAINT ck_credit_simulations_annual_effective_rate CHECK (annual_effective_rate IS NULL OR annual_effective_rate >= 0),
    CONSTRAINT ck_credit_simulations_installment_amount CHECK (installment_amount > 0),
    CONSTRAINT ck_credit_simulations_total_interest_amount CHECK (total_interest_amount >= 0),
    CONSTRAINT ck_credit_simulations_total_payable_amount CHECK (total_payable_amount > 0),
    CONSTRAINT ck_credit_simulations_income_commitment CHECK (income_commitment_percentage >= 0),
    CONSTRAINT ck_credit_simulations_calculation_method CHECK (calculation_method IN ('PRICE', 'SAC')),
    CONSTRAINT ck_credit_simulations_accepted_at CHECK (
        accepted = FALSE
        OR accepted_at IS NOT NULL
    )
);

CREATE INDEX idx_credit_simulations_proposal_id ON credit_simulations (proposal_id);
