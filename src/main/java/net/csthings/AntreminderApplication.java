package net.csthings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class)
public class AntreminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntreminderApplication.class, args);
    }

}
