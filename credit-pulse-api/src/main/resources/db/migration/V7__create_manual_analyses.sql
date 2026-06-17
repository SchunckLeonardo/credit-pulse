CREATE TABLE manual_analyses
(
    id          UUID                     NOT NULL DEFAULT gen_random_uuid(),
    proposal_id UUID                     NOT NULL,
    analyst_id  UUID,
    status      VARCHAR(30)              NOT NULL,
    decision    VARCHAR(30),
    reason      VARCHAR(1000),
    assigned_at TIMESTAMP WITH TIME ZONE,
    started_at  TIMESTAMP WITH TIME ZONE,
    finished_at TIMESTAMP WITH TIME ZONE,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version     BIGINT                   NOT NULL DEFAULT 0,
    CONSTRAINT pk_manual_analyses PRIMARY KEY (id),
    CONSTRAINT fk_manual_analyses_proposal FOREIGN KEY (proposal_id) REFERENCES credit_proposals (id),
    CONSTRAINT ck_manual_analyses_status CHECK (status IN ('PENDING', 'IN_PROGRESS', 'FINISHED')),
    CONSTRAINT ck_manual_analyses_decision CHECK (decision IS NULL OR decision IN ('APPROVED', 'REJECTED', 'RETURNED')),
    CONSTRAINT ck_manual_analyses_finished_decision CHECK (status <> 'FINISHED' OR decision IS NOT NULL),
    CONSTRAINT ck_manual_analyses_decision_reason CHECK (
        decision IS NULL
        OR (reason IS NOT NULL AND btrim(reason) <> '')
    )
);

CREATE INDEX idx_manual_analyses_proposal_id ON manual_analyses (proposal_id);
CREATE INDEX idx_manual_analyses_status ON manual_analyses (status);
CREATE UNIQUE INDEX uk_manual_analyses_one_active_by_proposal
    ON manual_analyses (proposal_id)
    WHERE status IN ('PENDING', 'IN_PROGRESS');
