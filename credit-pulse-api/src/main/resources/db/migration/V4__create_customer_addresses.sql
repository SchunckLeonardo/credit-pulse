CREATE TABLE customer_addresses
(
    id           UUID                     NOT NULL DEFAULT gen_random_uuid(),
    customer_id  UUID                     NOT NULL,
    address_type VARCHAR(20)              NOT NULL,
    street       VARCHAR(150)             NOT NULL,
    number       VARCHAR(20)              NOT NULL,
    complement   VARCHAR(100),
    neighborhood VARCHAR(100)             NOT NULL,
    city         VARCHAR(100)             NOT NULL,
    state        CHAR(2)                  NOT NULL,
    postal_code  VARCHAR(8)               NOT NULL,
    is_primary   BOOLEAN                  NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_customer_addresses PRIMARY KEY (id),
    CONSTRAINT fk_customer_addresses_customer FOREIGN KEY (customer_id) REFERENCES customers (id),
    CONSTRAINT ck_customer_addresses_address_type CHECK (address_type IN ('RESIDENTIAL', 'COMMERCIAL')),
    CONSTRAINT ck_customer_addresses_postal_code_digits CHECK (postal_code ~ '^[0-9]{8}$')
);

CREATE INDEX idx_customer_addresses_customer_id ON customer_addresses (customer_id);
CREATE UNIQUE INDEX uk_customer_addresses_one_primary
    ON customer_addresses (customer_id)
    WHERE is_primary;
