package net.csthings.config;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NonNull;

@Configuration
@ConfigurationProperties(prefix="websoc")
public @Data class WebSocSettings {
    @NotEmpty private String url;
    @NotEmpty private String cacheSearchPage;
    @NotEmpty private String cacheSearchForm;
}
