CREATE TABLE notification_template (
    id                VARCHAR(36)   NOT NULL PRIMARY KEY,
    product_store_id  VARCHAR(60)   NOT NULL,
    purpose           VARCHAR(80)   NOT NULL,
    name              VARCHAR(120)  NOT NULL,
    subject_template  VARCHAR(500)  NOT NULL,
    body_template     CLOB          NOT NULL,
    content_type      VARCHAR(40)   NOT NULL DEFAULT 'text/html',
    engine            VARCHAR(20)   NOT NULL DEFAULT 'FREEMARKER',
    locale            VARCHAR(20)   NOT NULL DEFAULT 'en',
    active            BOOLEAN       NOT NULL DEFAULT TRUE,
    version           BIGINT        NOT NULL DEFAULT 0,
    created_by        VARCHAR(120),
    created_stamp     TIMESTAMP     NOT NULL,
    last_updated_by   VARCHAR(120),
    last_updated_stamp TIMESTAMP    NOT NULL,
    CONSTRAINT uq_notification_template_store_purpose_locale
        UNIQUE (product_store_id, purpose, locale)
);

CREATE INDEX idx_notification_template_store ON notification_template (product_store_id);
CREATE INDEX idx_notification_template_purpose ON notification_template (purpose);
