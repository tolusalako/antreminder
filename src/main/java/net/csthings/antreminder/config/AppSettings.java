package net.csthings.antreminder.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="app")
public @Data class AppSettings {
    @NotEmpty private String encoding;
}
