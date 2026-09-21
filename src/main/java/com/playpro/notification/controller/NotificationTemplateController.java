package com.playpro.notification.controller;

import com.playpro.notification.dto.ActiveToggleRequest;
import com.playpro.notification.dto.NotificationTemplateRequest;
import com.playpro.notification.dto.NotificationTemplateResponse;
import com.playpro.notification.domain.OfbizEmailTypes;
import com.playpro.notification.service.NotificationTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification/templates")
@Validated
public class NotificationTemplateController {

    private final NotificationTemplateService templateService;

    public NotificationTemplateController(NotificationTemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public List<NotificationTemplateResponse> list(@RequestParam String productStoreId) {
        return templateService.listByStore(productStoreId);
    }

    /** OFBiz email types merged with store templates + active flags. */
    @GetMapping("/catalog")
    public List<Map<String, Object>> catalog(@RequestParam String productStoreId) {
        return templateService.listTypesCatalog(productStoreId);
    }

    @GetMapping("/email-types")
    public List<Map<String, Object>> emailTypes() {
        return OfbizEmailTypes.all().stream().map(OfbizEmailTypes.EmailTypeDef::toMap).collect(Collectors.toList());
    }

    @PostMapping("/ensure-defaults")
    public Map<String, Object> ensureDefaults(
            @RequestHeader(value = "X-User", required = false) String xUser,
            @RequestParam String productStoreId) {
        int created = templateService.ensureDefaults(productStoreId, principal(xUser));
        Map<String, Object> body = new HashMap<>();
        body.put("created", created);
        body.put("productStoreId", productStoreId);
        return body;
    }

    @GetMapping("/{id}")
    public NotificationTemplateResponse get(@PathVariable String id) {
        return templateService.get(id);
    }

    @PostMapping
    public ResponseEntity<NotificationTemplateResponse> create(
            @RequestHeader(value = "X-User", required = false) String xUser,
            @Valid @RequestBody NotificationTemplateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(templateService.create(request, principal(xUser)));
    }

    @PutMapping("/{id}")
    public NotificationTemplateResponse update(
            @RequestHeader(value = "X-User", required = false) String xUser,
            @PathVariable String id,
            @Valid @RequestBody NotificationTemplateRequest request) {
        return templateService.update(id, request, principal(xUser));
    }

    @PutMapping("/{id}/active")
    public NotificationTemplateResponse setActive(
            @RequestHeader(value = "X-User", required = false) String xUser,
            @PathVariable String id,
            @RequestBody ActiveToggleRequest request) {
        return templateService.setActive(id, request.isActive(), principal(xUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        templateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static String principal(String xUser) {
        return xUser == null || xUser.isBlank() ? "system" : xUser;
    }
}
