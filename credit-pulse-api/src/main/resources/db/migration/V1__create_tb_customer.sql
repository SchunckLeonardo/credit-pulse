CREATE TABLE tb_customer
(
    id             UUID NOT NULL,
    name           VARCHAR(255),
    cpf            VARCHAR(11),
    email          VARCHAR(100),
    phone          VARCHAR(20),
    birth_date     date,
    monthly_income NUMERIC(10, 2),
    profession     VARCHAR(255),
    status         VARCHAR(255),
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_customer PRIMARY KEY (id)
);

ALTER TABLE tb_customer
    ADD CONSTRAINT uc_tb_customer_cpf UNIQUE (cpf);

ALTER TABLE tb_customer
    ADD CONSTRAINT uc_tb_customer_email UNIQUE (email);