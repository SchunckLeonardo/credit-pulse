CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE tb_credit_proposal
    DROP CONSTRAINT fk_tb_credit_proposal_on_customer;

ALTER TABLE tb_customer
    RENAME TO customers;

ALTER TABLE customers
    RENAME CONSTRAINT pk_tb_customer TO pk_customers;

ALTER TABLE customers
    RENAME CONSTRAINT uc_tb_customer_cpf TO uk_customers_cpf;

ALTER TABLE customers
    RENAME CONSTRAINT uc_tb_customer_email TO uk_customers_email;

ALTER TABLE customers
    RENAME COLUMN name TO full_name;

UPDATE customers
SET full_name = COALESCE(full_name, 'UNKNOWN'),
    cpf = COALESCE(cpf, lpad(abs(hashtext(id::text))::text, 11, '0')),
    email = COALESCE(email, concat(id, '@unknown.local')),
    phone = COALESCE(phone, '0000000000'),
    birth_date = COALESCE(birth_date, CURRENT_DATE - INTERVAL '18 years'),
    monthly_income = COALESCE(NULLIF(monthly_income, 0), 0.01),
    profession = COALESCE(profession, 'UNKNOWN'),
    status = COALESCE(status, 'ACTIVE'),
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = COALESCE(updated_at, CURRENT_TIMESTAMP);

ALTER TABLE customers
    ADD COLUMN employment_type VARCHAR(30),
    ADD COLUMN blocked_reason VARCHAR(255),
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0;

ALTER TABLE customers
    ALTER COLUMN id SET DEFAULT gen_random_uuid(),
    ALTER COLUMN full_name TYPE VARCHAR(150),
    ALTER COLUMN full_name SET NOT NULL,
    ALTER COLUMN cpf SET NOT NULL,
    ALTER COLUMN birth_date SET NOT NULL,
    ALTER COLUMN email TYPE VARCHAR(150),
    ALTER COLUMN email SET NOT NULL,
    ALTER COLUMN phone SET NOT NULL,
    ALTER COLUMN monthly_income TYPE NUMERIC(15, 2),
    ALTER COLUMN monthly_income SET NOT NULL,
    ALTER COLUMN profession TYPE VARCHAR(100),
    ALTER COLUMN profession SET NOT NULL,
    ALTER COLUMN status TYPE VARCHAR(20),
    ALTER COLUMN status SET NOT NULL,
    ALTER COLUMN created_at TYPE TIMESTAMP WITH TIME ZONE USING created_at AT TIME ZONE 'UTC',
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP,
    ALTER COLUMN created_at SET NOT NULL,
    ALTER COLUMN updated_at TYPE TIMESTAMP WITH TIME ZONE USING updated_at AT TIME ZONE 'UTC',
    ALTER COLUMN updated_at SET DEFAULT CURRENT_TIMESTAMP,
    ALTER COLUMN updated_at SET NOT NULL;

ALTER TABLE customers
    ADD CONSTRAINT ck_customers_cpf_digits CHECK (cpf ~ '^[0-9]{11}$'),
    ADD CONSTRAINT ck_customers_monthly_income_positive CHECK (monthly_income > 0),
    ADD CONSTRAINT ck_customers_status CHECK (status IN ('ACTIVE', 'BLOCKED', 'INACTIVE')),
    ADD CONSTRAINT ck_customers_blocked_reason CHECK (
        status <> 'BLOCKED'
        OR (blocked_reason IS NOT NULL AND btrim(blocked_reason) <> '')
    );

CREATE INDEX idx_customers_status ON customers (status);
CREATE INDEX idx_customers_created_at ON customers (created_at);

ALTER TABLE tb_credit_proposal
    RENAME TO credit_proposals;

ALTER TABLE credit_proposals
    RENAME CONSTRAINT pk_tb_credit_proposal TO pk_credit_proposals_legacy;

ALTER TABLE credit_proposals
    RENAME COLUMN id TO legacy_id;

ALTER TABLE credit_proposals
    RENAME COLUMN installments TO installment_quantity;

ALTER TABLE credit_proposals
    RENAME COLUMN score TO current_score;

ALTER TABLE credit_proposals
    RENAME COLUMN decision_reason TO decision_reason_description;

UPDATE credit_proposals
SET customer_id = customer_id,
    requested_amount = COALESCE(requested_amount, 500),
    installment_quantity = COALESCE(installment_quantity, 3),
    purpose = COALESCE(purpose, 'OTHER'),
    status = COALESCE(status, 'PENDING_ANALYSIS'),
    created_at = COALESCE(created_at, CURRENT_TIMESTAMP),
    updated_at = COALESCE(updated_at, CURRENT_TIMESTAMP);

ALTER TABLE credit_proposals
    DROP CONSTRAINT pk_credit_proposals_legacy,
    ADD COLUMN id UUID NOT NULL DEFAULT gen_random_uuid(),
    ADD COLUMN risk_level VARCHAR(20),
    ADD COLUMN decision_reason_code VARCHAR(50),
    ADD COLUMN approved_amount NUMERIC(15, 2),
    ADD COLUMN expires_at TIMESTAMP WITH TIME ZONE,
    ADD COLUMN analyzed_at TIMESTAMP WITH TIME ZONE,
    ADD COLUMN cancelled_at TIMESTAMP WITH TIME ZONE,
    ADD COLUMN version BIGINT NOT NULL DEFAULT 0,
    ADD CONSTRAINT pk_credit_proposals PRIMARY KEY (id);

ALTER TABLE credit_proposals
    DROP COLUMN legacy_id;

ALTER TABLE credit_proposals
    ALTER COLUMN customer_id SET NOT NULL,
    ALTER COLUMN requested_amount TYPE NUMERIC(15, 2),
    ALTER COLUMN requested_amount SET NOT NULL,
    ALTER COLUMN installment_quantity SET NOT NULL,
    ALTER COLUMN purpose TYPE VARCHAR(50),
    ALTER COLUMN purpose SET NOT NULL,
    ALTER COLUMN status TYPE VARCHAR(40),
    ALTER COLUMN status SET NOT NULL,
    ALTER COLUMN decision_reason_description TYPE VARCHAR(500),
    ALTER COLUMN created_at TYPE TIMESTAMP WITH TIME ZONE USING created_at AT TIME ZONE 'UTC',
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP,
    ALTER COLUMN created_at SET NOT NULL,
    ALTER COLUMN updated_at TYPE TIMESTAMP WITH TIME ZONE USING updated_at AT TIME ZONE 'UTC',
    ALTER COLUMN updated_at SET DEFAULT CURRENT_TIMESTAMP,
    ALTER COLUMN updated_at SET NOT NULL;

ALTER TABLE credit_proposals
    ADD CONSTRAINT fk_credit_proposals_customer FOREIGN KEY (customer_id) REFERENCES customers (id),
    ADD CONSTRAINT ck_credit_proposals_requested_amount CHECK (requested_amount BETWEEN 500 AND 100000),
    ADD CONSTRAINT ck_credit_proposals_installment_quantity CHECK (installment_quantity BETWEEN 3 AND 60),
    ADD CONSTRAINT ck_credit_proposals_purpose CHECK (
        purpose IN ('PERSONAL', 'EDUCATION', 'HEALTH', 'VEHICLE', 'HOME_RENOVATION', 'DEBT_REFINANCING', 'OTHER')
    ),
    ADD CONSTRAINT ck_credit_proposals_status CHECK (
        status IN ('PENDING_ANALYSIS', 'PROCESSING', 'APPROVED', 'REJECTED', 'MANUAL_ANALYSIS', 'MANUAL_REVIEW', 'CANCELLED', 'EXPIRED')
    ),
    ADD CONSTRAINT ck_credit_proposals_risk_level CHECK (risk_level IS NULL OR risk_level IN ('LOW', 'MEDIUM', 'HIGH')),
    ADD CONSTRAINT ck_credit_proposals_approved_amount CHECK (
        approved_amount IS NULL
        OR (approved_amount > 0 AND approved_amount <= requested_amount)
    );

CREATE INDEX idx_credit_proposals_customer_id ON credit_proposals (customer_id);
CREATE INDEX idx_credit_proposals_status ON credit_proposals (status);
CREATE INDEX idx_credit_proposals_created_at ON credit_proposals (created_at);
CREATE INDEX idx_credit_proposals_customer_status ON credit_proposals (customer_id, status);
CREATE INDEX idx_credit_proposals_current_score ON credit_proposals (current_score);
CREATE INDEX idx_credit_proposals_risk_level ON credit_proposals (risk_level);
CREATE UNIQUE INDEX uk_credit_proposals_one_active_by_customer
    ON credit_proposals (customer_id)
    WHERE status IN ('PENDING_ANALYSIS', 'PROCESSING', 'MANUAL_ANALYSIS', 'MANUAL_REVIEW');
