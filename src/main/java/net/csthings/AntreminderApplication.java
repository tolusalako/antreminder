package net.csthings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;

import net.csthings.config.ModulesConfig;

@SpringBootApplication
public class AntreminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntreminderApplication.class, args);
    }
}

@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;

}
