package net.csthings.antreminder.services.reminder.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.csthings.antreminder.config.AppSettings;
import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.repo.ReminderDao;
import net.csthings.antreminder.services.account.utils.ValidationUtils;
import net.csthings.antreminder.services.email.EmailService;
import net.csthings.antreminder.services.email.exception.EmailException;

public class NotificationService {
    static Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private EmailService emailService;
    @Autowired
    private ReminderDao reminderDao;
    @Autowired
    AppSettings appSettings;
    private String apiUrl = "localhost:8080";
    private static String titleTemplate1 = "%s %s is now %s";
    private static String titleTemplate1_1 = "%s %s's status is now %s";
    private static String titleTemplate2 = "Some of your %s classes have updated status";

    private static String bodyTemplate2 = "The following classes in %s were updated:";
    private static String bodyListTemplate = "<li>%s %s: %s is now %s</li>";

    private static final String EMAIL_TEXT;

    private static final Set<String> template1StatusSet;
    static {
        template1StatusSet = new HashSet<>();
        template1StatusSet.add(ReminderStatus.OPEN);
        template1StatusSet.add(ReminderStatus.FULL);

        try {
            EMAIL_TEXT = FileUtils.readFileToString(new File("resources/email/email_reminder.html"),
                    Charset.forName("UTF-8"));
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @PostConstruct
    public void init() {
        apiUrl = appSettings.getHost();
    }

    public void sendNotifications(String dept, Map<String, List<ReminderDto>> accountsNotifications) {
        for (Entry<String, List<ReminderDto>> entry : accountsNotifications.entrySet()) {
            String recipient = entry.getKey();
            List<ReminderDto> notifications = entry.getValue();
            Calendar cal = Calendar.getInstance();
            // String time = StringUtils.join(cal.get(Calendar.HOUR), ":",
            // cal.get(Calendar.MINUTE),
            // cal.get(Calendar.AM_PM) == 1 ? "PM" : "AM");
            String subject = "";
            String text = "";

            if (notifications.size() > 1) {
                subject = String.format(titleTemplate2, dept);
                text = StringUtils.join(String.format(bodyTemplate2, dept), "\n");

            }
            else {
                ReminderDto onlyReminder = notifications.get(0);
                if (template1StatusSet.contains(onlyReminder.getStatus()))
                    subject = String.format(titleTemplate1, dept, onlyReminder.getNumber(), onlyReminder.getStatus());
                else
                    subject = String.format(titleTemplate1_1, dept, onlyReminder.getNumber(), onlyReminder.getStatus());
            }

            text = StringUtils.join(text, "<ul>");
            for (ReminderDto r : notifications) {
                reminderDao.updateEmailSent(r.getReminderId(), r.getStatus(), Calendar.getInstance().getTime());
                text = StringUtils.join(text,
                        String.format(bodyListTemplate, dept, r.getNumber(), r.getTitle(), r.getStatus()));
            }
            text = StringUtils.join(text, "</ul>");

            try {
                String emailText = StringUtils.replace(EMAIL_TEXT, ValidationUtils.PLACEHOLDER_USERNAME, recipient);
                emailText = StringUtils.replace(emailText, ValidationUtils.PLACEHOLDER_MESSAGE, text);
                emailText = StringUtils.replace(emailText, ValidationUtils.PLACEHOLDER_REMINDERS,
                        apiUrl + "/reminders");
                emailText = StringUtils.replace(emailText, ValidationUtils.PLACEHOLDER_SIGNOFF, "Antreminder Team");
                emailService.sendHtmlEmail(subject, emailText, new String[] { recipient }, null, null);
            }
            catch (EmailException e) {
                LOG.error("Could not send emails to {}", recipient, e);
            }
        }

    }

}
