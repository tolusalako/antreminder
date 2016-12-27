package net.csthings.antreminder.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "reminder.runner")
public @Data class RunnerSettings {
    @NotEmpty
    private String scanInterval;
    @NotEmpty
    private String scanUrl;
    @NotEmpty
    private String scanExpectedTitle;
}
