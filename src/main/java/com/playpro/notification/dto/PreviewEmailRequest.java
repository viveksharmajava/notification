package com.playpro.notification.dto;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

public class PreviewEmailRequest {

    @NotBlank
    private String subjectTemplate;

    @NotBlank
    private String bodyTemplate;

    private Map<String, Object> data = new HashMap<>();

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

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data != null ? data : new HashMap<>();
    }
}
