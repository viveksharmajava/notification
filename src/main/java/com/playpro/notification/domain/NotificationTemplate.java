package com.playpro.notification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import java.time.Instant;

@Entity
@Table(
        name = "notification_template",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_notification_template_store_purpose_locale",
                columnNames = {"product_store_id", "purpose", "locale"}
        )
)
public class NotificationTemplate {

    @Id
    @Column(length = 36, nullable = false)
    private String id;

    @Column(name = "product_store_id", length = 60, nullable = false)
    private String productStoreId;

    @Column(length = 80, nullable = false)
    private String purpose;

    @Column(length = 120, nullable = false)
    private String name;

    @Column(name = "subject_template", length = 500, nullable = false)
    private String subjectTemplate;

    @Lob
    @Column(name = "body_template", nullable = false)
    private String bodyTemplate;

    @Column(name = "content_type", length = 40, nullable = false)
    private String contentType = "text/html";

    @Column(length = 20, nullable = false)
    private String engine = "FREEMARKER";

    @Column(length = 20, nullable = false)
    private String locale = "en";

    @Column(nullable = false)
    private boolean active = true;

    @Version
    private Long version;

    @Column(name = "created_by", length = 120)
    private String createdBy;

    @Column(name = "created_stamp", nullable = false)
    private Instant createdStamp;

    @Column(name = "last_updated_by", length = 120)
    private String lastUpdatedBy;

    @Column(name = "last_updated_stamp", nullable = false)
    private Instant lastUpdatedStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductStoreId() {
        return productStoreId;
    }

    public void setProductStoreId(String productStoreId) {
        this.productStoreId = productStoreId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public void setSubjectTemplate(String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(Instant createdStamp) {
        this.createdStamp = createdStamp;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Instant getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public void setLastUpdatedStamp(Instant lastUpdatedStamp) {
        this.lastUpdatedStamp = lastUpdatedStamp;
    }
}
