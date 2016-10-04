package net.csthings.antreminder.services.reminder.util;

public class NotificationService {
    // static Logger LOG = LoggerFactory.getLogger(ReminderRunner.class);
    // private static EmailService emailService;
    //
    // private static String emailTemplate = "Hello\n\n%s %s's status changed to
    // %s around %s today.";
    //
    // private static String titleTemplate = "[AntReminder] %s %s is %s";
    //
    // @Inject
    // public NotificationService(EmailService emailService) {
    // NotificationService.emailService = emailService;
    // }
    //
    // private static void sendNotification(RunnerNotification
    // runnerNotification) {
    //
    // }
    //
    // public static void sendNotifications(Map<RunnerNotification, Set<String>>
    // runnerNotifications) {
    // for (Entry<RunnerNotification, Set<String>> entry :
    // runnerNotifications.entrySet()) {
    // Set<String> recipients = entry.getValue();
    // RunnerNotification key = entry.getKey();
    // Calendar cal = Calendar.getInstance();
    // String time = StringUtils.join(cal.get(Calendar.HOUR), ":",
    // cal.get(Calendar.MINUTE),
    // cal.get(Calendar.AM_PM) == 1 ? "PM" : "AM");
    // String subject = String.format(titleTemplate, key.getDept(),
    // key.getNumber(), key.getStatus());
    // String text = String.format(emailTemplate, key.getDept(),
    // key.getNumber(), key.getStatus(), time);
    // try {
    // emailService.sendEmail(subject, text, null, null, recipients.toArray(new
    // String[recipients.size()]));
    // }
    // catch (EmailException e) {
    // LOG.error("Could not send emails to {}", recipients.toString(), e);
    // }
    // }
    //
    // }

}
