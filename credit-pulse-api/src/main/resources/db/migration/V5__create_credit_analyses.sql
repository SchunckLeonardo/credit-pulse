CREATE TABLE credit_analyses
(
    id                           UUID                     NOT NULL DEFAULT gen_random_uuid(),
    proposal_id                  UUID                     NOT NULL,
    analysis_version             VARCHAR(20)              NOT NULL,
    external_score               INTEGER,
    internal_score               INTEGER                  NOT NULL,
    risk_level                   VARCHAR(20)              NOT NULL,
    income_commitment_percentage NUMERIC(5, 2)            NOT NULL,
    decision                     VARCHAR(30)              NOT NULL,
    decision_reason_code         VARCHAR(50)              NOT NULL,
    decision_reason_description  VARCHAR(500)             NOT NULL,
    rules_result                 JSONB,
    processing_time_ms           BIGINT,
    created_at                   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_credit_analyses PRIMARY KEY (id),
    CONSTRAINT fk_credit_analyses_proposal FOREIGN KEY (proposal_id) REFERENCES credit_proposals (id),
    CONSTRAINT ck_credit_analyses_risk_level CHECK (risk_level IN ('LOW', 'MEDIUM', 'HIGH')),
    CONSTRAINT ck_credit_analyses_income_commitment CHECK (income_commitment_percentage >= 0),
    CONSTRAINT ck_credit_analyses_decision CHECK (decision IN ('APPROVED', 'REJECTED', 'MANUAL_ANALYSIS')),
    CONSTRAINT ck_credit_analyses_processing_time CHECK (processing_time_ms IS NULL OR processing_time_ms >= 0)
);

CREATE INDEX idx_credit_analyses_proposal_id ON credit_analyses (proposal_id);
CREATE INDEX idx_credit_analyses_created_at ON credit_analyses (created_at);
