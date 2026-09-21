package com.playpro.notification.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * OFBiz ProductStoreEmailSetting emailType codes + display metadata.
 */
public final class OfbizEmailTypes {

    public static final String PRDS_ODR_CONFIRM = "PRDS_ODR_CONFIRM";
    public static final String PRDS_ODR_COMPLETE = "PRDS_ODR_COMPLETE";
    public static final String PRDS_ODR_BACKORDER = "PRDS_ODR_BACKORDER";
    public static final String PRDS_ODR_CHANGE = "PRDS_ODR_CHANGE";
    public static final String PRDS_ODR_PAYRETRY = "PRDS_ODR_PAYRETRY";
    public static final String PRDS_ODR_SHIP_COMPLT = "PRDS_ODR_SHIP_COMPLT";
    public static final String PRDS_RTN_ACCEPT = "PRDS_RTN_ACCEPT";
    public static final String PRDS_RTN_COMPLETE = "PRDS_RTN_COMPLETE";
    public static final String PRDS_RTN_CANCEL = "PRDS_RTN_CANCEL";
    public static final String PRDS_GC_PURCHASE = "PRDS_GC_PURCHASE";
    public static final String PRDS_GC_RELOAD = "PRDS_GC_RELOAD";
    public static final String PRDS_TELL_FRIEND = "PRDS_TELL_FRIEND";
    public static final String PRDS_PWD_RETRIEVE = "PRDS_PWD_RETRIEVE";
    public static final String PRDS_QUO_CONFIRM = "PRDS_QUO_CONFIRM";
    public static final String PARTY_REGIS_CONFIRM = "PARTY_REGIS_CONFIRM";
    public static final String PRDS_CUST_REGISTER = "PRDS_CUST_REGISTER";
    public static final String UPD_PRSNL_INF_CNFRM = "UPD_PRSNL_INF_CNFRM";
    public static final String PRDS_EMAIL_VERIFY = "PRDS_EMAIL_VERIFY";
    public static final String PRDS_CUST_ACTIVATED = "PRDS_CUST_ACTIVATED";
    public static final String CONT_NOTI_EMAIL = "CONT_NOTI_EMAIL";
    public static final String SUB_CONT_LIST_NOTI = "SUB_CONT_LIST_NOTI";
    public static final String UNSUB_CONT_LIST_VERI = "UNSUB_CONT_LIST_VERI";
    public static final String UNSUB_CONT_LIST_NOTI = "UNSUB_CONT_LIST_NOTI";
    public static final String CONT_EMAIL_TEMPLATE = "CONT_EMAIL_TEMPLATE";

    private static final Map<String, String> LEGACY = Map.of(
            "ORDER_CONFIRMATION", PRDS_ODR_CONFIRM,
            "ORDER_SHIPPED", PRDS_ODR_SHIP_COMPLT,
            "ORDER_CANCELLED", PRDS_ODR_CHANGE,
            "PASSWORD_RESET", PRDS_PWD_RETRIEVE,
            "WELCOME", PRDS_CUST_REGISTER
    );

    private static final List<EmailTypeDef> ALL;

    static {
        List<EmailTypeDef> list = new ArrayList<>();
        list.add(def(PRDS_ODR_CONFIRM, "Order confirmation",
                "Order Confirmation #${orderId!''}", "default-templates/order-confirmation.ftl"));
        list.add(def(PRDS_ODR_COMPLETE, "Order complete",
                "Your Order Is Complete #${orderId!''}", "default-templates/order-complete.ftl"));
        list.add(def(PRDS_ODR_BACKORDER, "Backorder notice",
                "Backorder Notification #${orderId!''}", "default-templates/order-backorder.ftl"));
        list.add(def(PRDS_ODR_CHANGE, "Order change",
                "Order Change Notification #${orderId!''}", "default-templates/order-change.ftl"));
        list.add(def(PRDS_ODR_PAYRETRY, "Payment retry",
                "Order Payment Notification #${orderId!''}", "default-templates/order-payment-retry.ftl"));
        list.add(def(PRDS_ODR_SHIP_COMPLT, "Shipment complete",
                "Shipment Complete Notification #${orderId!''}", "default-templates/order-shipped.ftl"));
        list.add(def(PRDS_RTN_ACCEPT, "Return accepted",
                "Return Accepted #${(returnHeader.returnId)!''}", "default-templates/return-accept.ftl"));
        list.add(def(PRDS_RTN_COMPLETE, "Return completed",
                "Return Completed #${(returnHeader.returnId)!''}", "default-templates/return-complete.ftl"));
        list.add(def(PRDS_RTN_CANCEL, "Return cancelled",
                "Return Cancelled #${(returnHeader.returnId)!''}", "default-templates/return-cancel.ftl"));
        list.add(def(PRDS_GC_PURCHASE, "Gift card purchase",
                "A Gift From ${senderName!''}!", "default-templates/gift-card-purchase.ftl"));
        list.add(def(PRDS_GC_RELOAD, "Gift card reload",
                "Gift Card Reload Results", "default-templates/gift-card-reload.ftl"));
        list.add(def(PRDS_TELL_FRIEND, "Tell a friend",
                "${sendFrom!''} has sent you a link!", "default-templates/tell-friend.ftl"));
        list.add(def(PRDS_PWD_RETRIEVE, "Password reminder",
                "Password Reminder (${(userLogin.userLoginId)!''})", "default-templates/password-reset.ftl"));
        list.add(def(PRDS_QUO_CONFIRM, "Quote confirmation",
                "Quote Confirmation #${quoteId!''}", "default-templates/quote-confirm.ftl"));
        list.add(def(PARTY_REGIS_CONFIRM, "Party registration confirm",
                "New Account Created", "default-templates/party-register.ftl"));
        list.add(def(PRDS_CUST_REGISTER, "Customer registration",
                "New Account Created", "default-templates/customer-register.ftl"));
        list.add(def(UPD_PRSNL_INF_CNFRM, "Personal info updated",
                "Personal Information Updated", "default-templates/personal-info-updated.ftl"));
        list.add(def(PRDS_EMAIL_VERIFY, "Email verification",
                "Email Address Verification", "default-templates/email-verify.ftl"));
        list.add(def(PRDS_CUST_ACTIVATED, "Account activated",
                "Account Activated", "default-templates/account-activated.ftl"));
        list.add(def(CONT_NOTI_EMAIL, "Contact-us notification",
                "Contact-us Information Notification", "default-templates/contact-us.ftl"));
        list.add(def(SUB_CONT_LIST_NOTI, "Subscribe contact list",
                "Subscribe Contact List", "default-templates/contact-subscribe.ftl"));
        list.add(def(UNSUB_CONT_LIST_VERI, "Unsubscribe verify",
                "Verify Unsubscribe Contact List", "default-templates/contact-unsub-verify.ftl"));
        list.add(def(UNSUB_CONT_LIST_NOTI, "Unsubscribe contact list",
                "Unsubscribe Contact List", "default-templates/contact-unsubscribe.ftl"));
        list.add(def(CONT_EMAIL_TEMPLATE, "Contact list email template",
                "Newsletter", "default-templates/contact-list-template.ftl"));
        ALL = Collections.unmodifiableList(list);
    }

    private OfbizEmailTypes() {
    }

    public static List<EmailTypeDef> all() {
        return ALL;
    }

    public static String normalize(String purpose) {
        if (purpose == null || purpose.isBlank()) {
            return purpose;
        }
        String trimmed = purpose.trim();
        return LEGACY.getOrDefault(trimmed, trimmed);
    }

    private static EmailTypeDef def(String code, String label, String subject, String bodyClasspath) {
        return new EmailTypeDef(code, label, subject, bodyClasspath);
    }

    public static final class EmailTypeDef {
        private final String code;
        private final String label;
        private final String defaultSubject;
        private final String bodyClasspath;

        public EmailTypeDef(String code, String label, String defaultSubject, String bodyClasspath) {
            this.code = code;
            this.label = label;
            this.defaultSubject = defaultSubject;
            this.bodyClasspath = bodyClasspath;
        }

        public String getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        public String getDefaultSubject() {
            return defaultSubject;
        }

        public String getBodyClasspath() {
            return bodyClasspath;
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("code", code);
            map.put("label", label);
            map.put("defaultSubject", defaultSubject);
            return map;
        }
    }
}
