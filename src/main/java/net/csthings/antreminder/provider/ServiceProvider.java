/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package net.csthings.antreminder.provider;

import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.csthings.antreminder.config.MailSettings;
import net.csthings.antreminder.services.account.AccountService;
import net.csthings.antreminder.services.account.LoginService;
import net.csthings.antreminder.services.account.impl.AccountServiceImpl;
import net.csthings.antreminder.services.account.impl.LoginServiceImpl;
import net.csthings.antreminder.services.email.EmailService;
import net.csthings.antreminder.services.email.impl.MailGunEmailServiceImpl;
import net.csthings.antreminder.services.reminder.ReminderService;
import net.csthings.antreminder.services.reminder.impl.ReminderServiceImpl;
import net.csthings.antreminder.services.reminder.util.NotificationService;
import net.csthings.antreminder.websoc.impl.WebSocServiceImpl;
import net.csthings.antreminder.websoc.service.WebSocService;

@Configuration
public class ServiceProvider {

    @Bean
    @Singleton
    public static WebSocService webSocService() {
        return new WebSocServiceImpl();
    }

    @Bean
    @Singleton
    public static AccountService accountService() {
        return new AccountServiceImpl();
    }

    @Bean
    @Singleton
    public static LoginService loginService() {
        return new LoginServiceImpl();
    }

    @Bean
    @Singleton
    public static ReminderService reminderService() {
        return new ReminderServiceImpl();
    }

    @Bean
    @Singleton
    public static NotificationService notificationService() {
        return new NotificationService();
    }

    @Bean
    @Singleton
    public static EmailService emailService() {
        return new MailGunEmailServiceImpl();
    }

}
