package com.playpro.notification.bootstrap;

import com.playpro.notification.domain.OfbizEmailTypes;
import com.playpro.notification.service.NotificationTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultTemplateSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DefaultTemplateSeeder.class);
    private static final String DEFAULT_STORE = "OFBIZ_STORE";

    private final NotificationTemplateService templateService;

    public DefaultTemplateSeeder(NotificationTemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public void run(ApplicationArguments args) {
        int created = templateService.ensureDefaults(DEFAULT_STORE, "system-seed");
        log.info("Notification template seed for {}: created/ensured {} missing types (catalog size={})",
                DEFAULT_STORE, created, OfbizEmailTypes.all().size());
    }
}
