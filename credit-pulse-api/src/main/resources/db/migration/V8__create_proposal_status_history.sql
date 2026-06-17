CREATE TABLE proposal_status_history
(
    id                 UUID                     NOT NULL DEFAULT gen_random_uuid(),
    proposal_id        UUID                     NOT NULL,
    previous_status    VARCHAR(40),
    new_status         VARCHAR(40)              NOT NULL,
    reason_code        VARCHAR(50),
    reason_description VARCHAR(500),
    changed_by_type    VARCHAR(20)              NOT NULL,
    changed_by_id      UUID,
    created_at         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_proposal_status_history PRIMARY KEY (id),
    CONSTRAINT fk_proposal_status_history_proposal FOREIGN KEY (proposal_id) REFERENCES credit_proposals (id),
    CONSTRAINT ck_proposal_status_history_previous_status CHECK (
        previous_status IS NULL
        OR previous_status IN ('PENDING_ANALYSIS', 'PROCESSING', 'APPROVED', 'REJECTED', 'MANUAL_ANALYSIS', 'MANUAL_REVIEW', 'CANCELLED', 'EXPIRED')
    ),
    CONSTRAINT ck_proposal_status_history_new_status CHECK (
        new_status IN ('PENDING_ANALYSIS', 'PROCESSING', 'APPROVED', 'REJECTED', 'MANUAL_ANALYSIS', 'MANUAL_REVIEW', 'CANCELLED', 'EXPIRED')
    ),
    CONSTRAINT ck_proposal_status_history_changed_by_type CHECK (changed_by_type IN ('SYSTEM', 'CUSTOMER', 'USER'))
);

CREATE INDEX idx_proposal_status_history_proposal_id ON proposal_status_history (proposal_id);
CREATE INDEX idx_proposal_status_history_created_at ON proposal_status_history (created_at);
