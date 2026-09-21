package com.playpro.notification.service;

import com.playpro.notification.domain.NotificationTemplate;
import com.playpro.notification.domain.OfbizEmailTypes;
import com.playpro.notification.dto.NotificationTemplateRequest;
import com.playpro.notification.dto.NotificationTemplateResponse;
import com.playpro.notification.repository.NotificationTemplateRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationTemplateService {

    private final NotificationTemplateRepository repository;

    public NotificationTemplateService(NotificationTemplateRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<NotificationTemplateResponse> listByStore(String productStoreId) {
        return repository.findByProductStoreIdOrderByPurposeAsc(productStoreId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> listTypesCatalog(String productStoreId) {
        Map<String, NotificationTemplate> byPurpose = repository
                .findByProductStoreIdOrderByPurposeAsc(productStoreId).stream()
                .collect(Collectors.toMap(NotificationTemplate::getPurpose, t -> t, (a, b) -> a, LinkedHashMap::new));

        List<Map<String, Object>> rows = new ArrayList<>();
        for (OfbizEmailTypes.EmailTypeDef def : OfbizEmailTypes.all()) {
            Map<String, Object> row = new LinkedHashMap<>(def.toMap());
            NotificationTemplate entity = byPurpose.get(def.getCode());
            if (entity != null) {
                row.put("template", toResponse(entity));
                row.put("configured", true);
                row.put("active", entity.isActive());
            } else {
                row.put("template", null);
                row.put("configured", false);
                row.put("active", false);
            }
            rows.add(row);
        }
        return rows;
    }

    @Transactional(readOnly = true)
    public NotificationTemplateResponse get(String id) {
        return toResponse(require(id));
    }

    @Transactional(readOnly = true)
    public Optional<NotificationTemplate> findActive(String productStoreId, String purpose, String locale) {
        String normalized = OfbizEmailTypes.normalize(purpose);
        String loc = normalizeLocale(locale);
        Optional<NotificationTemplate> hit = repository
                .findByProductStoreIdAndPurposeAndLocaleAndActiveTrue(productStoreId, normalized, loc);
        if (hit.isPresent()) {
            return hit;
        }
        if (!"en".equals(loc)) {
            return repository.findByProductStoreIdAndPurposeAndLocaleAndActiveTrue(
                    productStoreId, normalized, "en");
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public NotificationTemplate requireActive(String productStoreId, String purpose, String locale) {
        return findActive(productStoreId, purpose, locale)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No active template for store=" + productStoreId
                                + " purpose=" + OfbizEmailTypes.normalize(purpose)
                                + " locale=" + normalizeLocale(locale)));
    }

    @Transactional
    public NotificationTemplateResponse create(NotificationTemplateRequest request, String principal) {
        String purpose = OfbizEmailTypes.normalize(request.getPurpose());
        String locale = normalizeLocale(request.getLocale());
        if (repository.existsByProductStoreIdAndPurposeAndLocale(
                request.getProductStoreId(), purpose, locale)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Template already exists for this store/purpose/locale");
        }
        Instant now = Instant.now();
        NotificationTemplate entity = new NotificationTemplate();
        entity.setId(UUID.randomUUID().toString());
        apply(entity, request, purpose, locale);
        entity.setCreatedBy(principal);
        entity.setCreatedStamp(now);
        entity.setLastUpdatedBy(principal);
        entity.setLastUpdatedStamp(now);
        return toResponse(repository.save(entity));
    }

    @Transactional
    public NotificationTemplateResponse update(String id, NotificationTemplateRequest request, String principal) {
        NotificationTemplate entity = require(id);
        String purpose = OfbizEmailTypes.normalize(request.getPurpose());
        String locale = normalizeLocale(request.getLocale());
        repository.findByProductStoreIdAndPurposeAndLocale(
                        request.getProductStoreId(), purpose, locale)
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Another template already uses this store/purpose/locale");
                });
        apply(entity, request, purpose, locale);
        entity.setLastUpdatedBy(principal);
        entity.setLastUpdatedStamp(Instant.now());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public NotificationTemplateResponse setActive(String id, boolean active, String principal) {
        NotificationTemplate entity = require(id);
        entity.setActive(active);
        entity.setLastUpdatedBy(principal);
        entity.setLastUpdatedStamp(Instant.now());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found");
        }
        repository.deleteById(id);
    }

    @Transactional
    public int ensureDefaults(String productStoreId, String principal) {
        int created = 0;
        for (OfbizEmailTypes.EmailTypeDef def : OfbizEmailTypes.all()) {
            if (repository.existsByProductStoreIdAndPurposeAndLocale(productStoreId, def.getCode(), "en")) {
                continue;
            }
            upsertSeed(productStoreId, def.getCode(), def.getLabel(), def.getDefaultSubject(),
                    readClasspath(def.getBodyClasspath()), principal);
            created++;
        }
        return created;
    }

    @Transactional
    public NotificationTemplateResponse upsertSeed(
            String productStoreId,
            String purpose,
            String name,
            String subject,
            String body,
            String principal) {
        String locale = "en";
        String normalized = OfbizEmailTypes.normalize(purpose);
        NotificationTemplate entity = repository
                .findByProductStoreIdAndPurposeAndLocale(productStoreId, normalized, locale)
                .orElseGet(NotificationTemplate::new);
        boolean isNew = entity.getId() == null;
        Instant now = Instant.now();
        if (isNew) {
            entity.setId(UUID.randomUUID().toString());
            entity.setCreatedBy(principal);
            entity.setCreatedStamp(now);
            entity.setActive(true);
        }
        entity.setProductStoreId(productStoreId);
        entity.setPurpose(normalized);
        entity.setName(name);
        entity.setSubjectTemplate(subject);
        entity.setBodyTemplate(body);
        entity.setContentType("text/html");
        entity.setEngine("FREEMARKER");
        entity.setLocale(locale);
        entity.setLastUpdatedBy(principal);
        entity.setLastUpdatedStamp(now);
        return toResponse(repository.save(entity));
    }

    private NotificationTemplateResponse toResponse(NotificationTemplate entity) {
        NotificationTemplateResponse dto = NotificationTemplateResponse.from(entity);
        OfbizEmailTypes.all().stream()
                .filter(d -> d.getCode().equals(entity.getPurpose()))
                .findFirst()
                .ifPresent(d -> dto.setLabel(d.getLabel()));
        return dto;
    }

    private NotificationTemplate require(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found"));
    }

    private static void apply(NotificationTemplate entity, NotificationTemplateRequest request,
                              String purpose, String locale) {
        entity.setProductStoreId(request.getProductStoreId().trim());
        entity.setPurpose(purpose);
        entity.setName(request.getName().trim());
        entity.setSubjectTemplate(request.getSubjectTemplate());
        entity.setBodyTemplate(request.getBodyTemplate());
        entity.setContentType(request.getContentType() != null ? request.getContentType() : "text/html");
        entity.setEngine("FREEMARKER");
        entity.setLocale(locale);
        entity.setActive(request.getActive() == null || request.getActive());
    }

    private static String normalizeLocale(String locale) {
        if (locale == null || locale.trim().isEmpty()) {
            return "en";
        }
        return locale.trim().toLowerCase();
    }

    private static String readClasspath(String path) {
        try {
            return StreamUtils.copyToString(new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return "<html><body><p>" + path + " missing — edit this template in admin.</p></body></html>";
        }
    }
}
