package com.playpro.notification.service;

import com.playpro.notification.domain.NotificationTemplate;
import com.playpro.notification.domain.OfbizEmailTypes;
import com.playpro.notification.dto.PreviewEmailRequest;
import com.playpro.notification.dto.SendEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailNotificationService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    private final NotificationTemplateService templateService;
    private final FreemarkerTemplateRenderer renderer;
    private final JavaMailSender mailSender;

    @Value("${notification.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${notification.mail.from:noreply@localhost}")
    private String fromAddress;

    public EmailNotificationService(NotificationTemplateService templateService,
                                    FreemarkerTemplateRenderer renderer,
                                    JavaMailSender mailSender) {
        this.templateService = templateService;
        this.renderer = renderer;
        this.mailSender = mailSender;
    }

    public Map<String, Object> send(SendEmailRequest request) {
        String purpose = OfbizEmailTypes.normalize(request.getPurpose());
        Optional<NotificationTemplate> templateOpt = templateService.findActive(
                request.getProductStoreId(), purpose, request.getLocale());

        if (templateOpt.isEmpty()) {
            log.warn("Skip email purpose={} store={} — no active template",
                    purpose, request.getProductStoreId());
            return result("SKIPPED_DISABLED_OR_MISSING", null, null, null);
        }

        NotificationTemplate template = templateOpt.get();
        Map<String, Object> model = request.getData() != null ? request.getData() : Map.of();
        try {
            String subject = request.getSubjectOverride() != null && !request.getSubjectOverride().isBlank()
                    ? request.getSubjectOverride()
                    : renderer.render(template.getSubjectTemplate(), model);
            String body = renderer.render(template.getBodyTemplate(), model);

            if (!mailEnabled) {
                log.info(
                        "Mail disabled — would send purpose={} to={} subject={} bodyLength={}",
                        purpose, request.getTo(), subject, body.length());
                return result("LOGGED", subject, body, template.getId());
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(request.getTo().toArray(new String[0]));
            if (request.getCc() != null && !request.getCc().isEmpty()) {
                helper.setCc(request.getCc().toArray(new String[0]));
            }
            if (request.getBcc() != null && !request.getBcc().isEmpty()) {
                helper.setBcc(request.getBcc().toArray(new String[0]));
            }
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
            log.info("Sent email purpose={} to={} templateId={}", purpose, request.getTo(), template.getId());
            return result("SENT", subject, body, template.getId());
        } catch (Exception ex) {
            // Fail-soft: callers treat send as best-effort; never bubble as hard failure.
            log.error("Email send failed purpose={} to={}: {}", purpose, request.getTo(), ex.getMessage(), ex);
            return result("FAILED", null, null, template.getId());
        }
    }

    public Map<String, Object> preview(PreviewEmailRequest request) {
        Map<String, Object> model = request.getData() != null ? request.getData() : Map.of();
        String subject = renderer.render(request.getSubjectTemplate(), model);
        String body = renderer.render(request.getBodyTemplate(), model);
        Map<String, Object> out = new HashMap<>();
        out.put("subject", subject);
        out.put("body", body);
        return out;
    }

    private static Map<String, Object> result(String status, String subject, String body, String templateId) {
        Map<String, Object> out = new HashMap<>();
        out.put("status", status);
        out.put("subject", subject);
        out.put("body", body);
        out.put("templateId", templateId);
        return out;
    }
}
