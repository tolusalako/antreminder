package net.csthings.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="websoc")
public @Data class WebSocSettings {
    @NotEmpty private String url;
    @NotEmpty private String cacheSearchPage;
    @NotEmpty private String cacheSearchForm;
}
