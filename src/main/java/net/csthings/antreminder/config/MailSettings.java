package net.csthings.antreminder.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "mail")
public @Data class MailSettings {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String key;
}
