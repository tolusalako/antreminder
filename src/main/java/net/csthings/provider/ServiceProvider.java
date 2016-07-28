package net.csthings.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.csthings.antreminder.websoc.impl.WebSocServiceImpl;
import net.csthings.antreminder.websoc.service.WebSocService;

@Configuration
public class ServiceProvider {
    
    @Bean
    public static WebSocService webSocService() {
            return new WebSocServiceImpl();
    }
}
