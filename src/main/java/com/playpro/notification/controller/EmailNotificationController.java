package com.playpro.notification.controller;

import com.playpro.notification.dto.PreviewEmailRequest;
import com.playpro.notification.dto.SendEmailRequest;
import com.playpro.notification.service.EmailNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/notification/email")
@Validated
public class EmailNotificationController {

    private final EmailNotificationService emailNotificationService;

    public EmailNotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    /**
     * Called by orders, party, and other services to send templated email.
     */
    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> send(@Valid @RequestBody SendEmailRequest request) {
        return ResponseEntity.ok(emailNotificationService.send(request));
    }

    @PostMapping("/preview")
    public ResponseEntity<Map<String, Object>> preview(@Valid @RequestBody PreviewEmailRequest request) {
        return ResponseEntity.ok(emailNotificationService.preview(request));
    }
}
