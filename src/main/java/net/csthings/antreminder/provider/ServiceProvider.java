package net.csthings.antreminder.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.csthings.antreminder.services.account.AccountService;
import net.csthings.antreminder.services.account.LoginService;
import net.csthings.antreminder.services.account.impl.AccountServiceImpl;
import net.csthings.antreminder.services.account.impl.LoginServiceImpl;
import net.csthings.antreminder.services.reminder.ReminderService;
import net.csthings.antreminder.services.reminder.impl.ReminderServiceImpl;
import net.csthings.antreminder.websoc.impl.WebSocServiceImpl;
import net.csthings.antreminder.websoc.service.WebSocService;

@Configuration
public class ServiceProvider {

    @Bean
    public static WebSocService webSocService() {
        return new WebSocServiceImpl();
    }

    @Bean
    public static AccountService accountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public static LoginService loginService() {
        return new LoginServiceImpl();
    }

    @Bean
    public static ReminderService reminderService() {
        return new ReminderServiceImpl();
    }

}
