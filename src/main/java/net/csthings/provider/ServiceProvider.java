package net.csthings.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.csthings.antreminder.websoc.WebSocService;
import net.csthings.antreminder.websoc.WebSocServiceImpl;

@Configuration
public class ServiceProvider {
    
    @Bean
    public static WebSocService webSocService() {
            return new WebSocServiceImpl();
    }
}
