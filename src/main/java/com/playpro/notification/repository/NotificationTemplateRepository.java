package com.playpro.notification.repository;

import com.playpro.notification.domain.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, String> {

    List<NotificationTemplate> findByProductStoreIdOrderByPurposeAsc(String productStoreId);

    Optional<NotificationTemplate> findByProductStoreIdAndPurposeAndLocaleAndActiveTrue(
            String productStoreId, String purpose, String locale);

    Optional<NotificationTemplate> findByProductStoreIdAndPurposeAndLocale(
            String productStoreId, String purpose, String locale);

    boolean existsByProductStoreIdAndPurposeAndLocale(
            String productStoreId, String purpose, String locale);
}
