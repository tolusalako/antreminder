package net.csthings.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 * Last edited: Jul 28, 2016
 * @purpose - Generates settings from property file
 */
@Configuration
@ConfigurationProperties(prefix = "websoc")
public @Data class WebSocSettings {
    @NotEmpty
    private String baseUrl;
    @NotEmpty
    private String formUrl;
    @NotEmpty
    private String searchUrl;
    @NotEmpty
    private String cacheSearchPage;
    @NotEmpty
    private String cacheSearchForm;
}
