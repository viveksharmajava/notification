package com.playpro.notification.dto;

import com.playpro.notification.domain.NotificationTemplate;

import java.time.Instant;

public class NotificationTemplateResponse {

    private String id;
    private String productStoreId;
    private String purpose;
    private String name;
    private String subjectTemplate;
    private String bodyTemplate;
    private String contentType;
    private String engine;
    private String locale;
    private boolean active;
    private Long version;
    private Instant createdStamp;
    private Instant lastUpdatedStamp;
    private String label;

    public static NotificationTemplateResponse from(NotificationTemplate entity) {
        NotificationTemplateResponse dto = new NotificationTemplateResponse();
        dto.id = entity.getId();
        dto.productStoreId = entity.getProductStoreId();
        dto.purpose = entity.getPurpose();
        dto.name = entity.getName();
        dto.subjectTemplate = entity.getSubjectTemplate();
        dto.bodyTemplate = entity.getBodyTemplate();
        dto.contentType = entity.getContentType();
        dto.engine = entity.getEngine();
        dto.locale = entity.getLocale();
        dto.active = entity.isActive();
        dto.version = entity.getVersion();
        dto.createdStamp = entity.getCreatedStamp();
        dto.lastUpdatedStamp = entity.getLastUpdatedStamp();
        return dto;
    }

    public String getId() {
        return id;
    }

    public String getProductStoreId() {
        return productStoreId;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getName() {
        return name;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public String getContentType() {
        return contentType;
    }

    public String getEngine() {
        return engine;
    }

    public String getLocale() {
        return locale;
    }

    public boolean isActive() {
        return active;
    }

    public Long getVersion() {
        return version;
    }

    public Instant getCreatedStamp() {
        return createdStamp;
    }

    public Instant getLastUpdatedStamp() {
        return lastUpdatedStamp;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
