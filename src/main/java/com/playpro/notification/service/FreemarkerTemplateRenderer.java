package com.playpro.notification.service;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class FreemarkerTemplateRenderer {

    public String render(String templateSource, Map<String, Object> data) {
        if (templateSource == null) {
            return "";
        }
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);

            StringTemplateLoader loader = new StringTemplateLoader();
            String name = "tpl-" + UUID.randomUUID();
            loader.putTemplate(name, templateSource);
            cfg.setTemplateLoader(loader);

            Template template = cfg.getTemplate(name);
            StringWriter writer = new StringWriter();
            template.process(data != null ? data : Collections.emptyMap(), writer);
            return writer.toString();
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to render Freemarker template: " + ex.getMessage(), ex);
        }
    }
}
