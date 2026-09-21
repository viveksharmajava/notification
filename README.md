# Notification service

Central email notification microservice. Admins edit Freemarker (`.ftl`) templates per store in catalog-admin; orders/party/etc. call the send API with dynamic data.

## Recommendations (template engine & storage)

| Option | Verdict |
|--------|---------|
| **Freemarker (`.ftl`)** | **Use this** — same family as OFBiz email templates, excellent for HTML emails + dynamic lists (`<#list items>`), Spring Boot support |
| Thymeleaf | Also good in Spring; slightly heavier for string/DB templates |
| Raw HTML + replace | Fragile for nested order lines |
| **JSP** | **Avoid** — tied to servlet container, not suited to email rendering |

| Storage | Verdict |
|---------|---------|
| **DB (editable) + classpath defaults (seed)** | **Best for your admin UI requirement** |
| Source-only (OFBiz style) | Best for version control / code review, weak for non-dev admin editing |
| DB-only with no defaults | Works, but first deploy has empty templates |

**Hybrid (what this service does):** seed default `.ftl` from classpath into DB once; admins customize in UI; runtime always reads **DB**.

## Run locally

```powershell
cd C:\vivek\project\notification
mvn spring-boot:run
```

Port **8087**. Mail is **off** by default (`notification.mail.enabled=false`) — send API logs the rendered email.

Enable SMTP:

```powershell
$env:NOTIFICATION_MAIL_ENABLED="true"
$env:NOTIFICATION_MAIL_FROM="you@gmail.com"
$env:MAIL_USERNAME="you@gmail.com"
$env:MAIL_PASSWORD="app-password"
mvn spring-boot:run
```

## APIs

### Admin — templates

```http
GET    /notification/templates?productStoreId=OFBIZ_STORE
POST   /notification/templates
PUT    /notification/templates/{id}
DELETE /notification/templates/{id}
```

### Send (orders / party / etc.)

```http
POST /notification/email/send
Content-Type: application/json

{
  "to": ["customer@example.com"],
  "purpose": "ORDER_CONFIRMATION",
  "productStoreId": "OFBIZ_STORE",
  "data": {
    "customerName": "Vivek",
    "orderId": "ORD-1001",
    "orderTotal": "1999.00",
    "currency": "INR",
    "items": [
      { "name": "Yonex Astrox Attack 9", "qty": 1, "price": "1549" }
    ]
  }
}
```

### Preview

```http
POST /notification/email/preview
```

## OFBiz email types

Templates use OFBiz `ProductStoreEmailSetting.emailType` codes (`PRDS_ODR_CONFIRM`, `PRDS_PWD_RETRIEVE`, …).
Catalog-admin **Stores → Notifications** lists all types with an **On/Off** enable switch and per-row **Preview**.

Ensure defaults for a store:

```http
POST /notification/templates/ensure-defaults?productStoreId=OFBIZ_STORE
```

Toggle:

```http
PUT /notification/templates/{id}/active
{ "active": true }
```
