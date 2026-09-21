package com.playpro.notification.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendEmailRequest {

    @NotEmpty
    private List<@Email @NotBlank String> to = new ArrayList<>();

    private List<@Email String> cc = new ArrayList<>();

    private List<@Email String> bcc = new ArrayList<>();

    @NotBlank
    private String purpose;

    @NotBlank
    private String productStoreId;

    private String locale = "en";

    private String subjectOverride;

    private Map<String, Object> data = new HashMap<>();

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to != null ? to : new ArrayList<>();
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc != null ? cc : new ArrayList<>();
    }

    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc != null ? bcc : new ArrayList<>();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getProductStoreId() {
        return productStoreId;
    }

    public void setProductStoreId(String productStoreId) {
        this.productStoreId = productStoreId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSubjectOverride() {
        return subjectOverride;
    }

    public void setSubjectOverride(String subjectOverride) {
        this.subjectOverride = subjectOverride;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data != null ? data : new HashMap<>();
    }
}
