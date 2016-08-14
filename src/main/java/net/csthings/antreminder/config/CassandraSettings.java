package net.csthings.antreminder.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "cassandra")
public @Data class CassandraSettings {
    @NotEmpty
    private String contactPoints;

    @NotEmpty
    private String keyspace;

    @NotEmpty
    private String port;

    public int getPort() {
        return Integer.valueOf(port);
    }
}
