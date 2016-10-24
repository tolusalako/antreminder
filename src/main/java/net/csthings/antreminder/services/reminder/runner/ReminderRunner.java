package net.csthings.antreminder.services.reminder.runner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.csthings.antreminder.config.RunnerSettings;
import net.csthings.antreminder.entity.dto.AccountDto;
import net.csthings.antreminder.entity.dto.AccountReminderDto;
import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.repo.AccountReminderDao;
import net.csthings.antreminder.repo.ReminderDao;
import net.csthings.antreminder.services.ServiceException;
import net.csthings.antreminder.services.email.EmailService;
import net.csthings.antreminder.services.websoc.WebsocScrapper;
import net.csthings.antreminder.services.websoc.WebsocUtils;

/**
 * Created on: Aug 26, 2016
 * @author Toluwanimi Salako
 * Last edited: Aug 26, 2016
 * @purpose - Searches webreg for updated class status and notifies those who are listening for a specific status
 */

@Component
@EnableScheduling
public class ReminderRunner {
    Logger LOG = LoggerFactory.getLogger(ReminderRunner.class);

    private static final int MAX_THREAD_COUNT = 10;
    private static final long scanInterval = 300000; // 5 min
    private String url;

    @Autowired
    private EmailService emailService;
    @Autowired
    private RunnerSettings runnerSettings;
    @Autowired
    private ReminderDao reminderDao;
    @Autowired
    private AccountReminderDao accountReminderDao;
    private ExecutorService executors;
    private WebsocScrapper scrapper;

    @PostConstruct
    public void init() {

        executors = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        url = runnerSettings.getScanUrl();
        // scanInterval = Long.valueOf(runnerSettings.getScanInterval());

        scrapper = new WebsocScrapper(url, runnerSettings.getScanExpectedTitle());
    }

    @Scheduled(fixedRate = scanInterval)
    public void scan() {
        List<ReminderDto> reminders = reminderDao.getRemindersGroupDeptId();
        if (reminders.isEmpty())
            return;
        Map<String, Set<String>> reminderSetReq = new HashMap<>();
        String lastDept = reminders.get(0).getDept();
        for (ReminderDto r : reminders) {
            if (!r.getDept().equals(lastDept) && !reminderSetReq.isEmpty()) {
                executors.submit(() -> {
                    Document doc = null;
                    try {
                        doc = scrapper.searchDept(r.getDept());
                    }
                    catch (ServiceException e) {
                        LOG.error("Could not search for dept: {}", r.getDept(), e);
                    }
                    Map<String, Set<String>> reminderSetReqCopy = null;
                    synchronized (reminderSetReq) {
                        reminderSetReqCopy = new HashMap<>(reminderSetReq);
                        reminderSetReq.clear();
                    }
                    Map<String, String> updates = scrapper.getMatchingClasses(doc, reminderSetReqCopy);
                    if (!updates.isEmpty()) {
                        LOG.info("Found the following: {}", updates);
                        // final List<AccountReminderDto> finalReminders =
                        // reminders;
                        handleCourseChanges(r.getDept(), updates);
                    }
                    else {
                        LOG.info("Found no matches for {}", r.getDept());
                    }
                });
            }
            reminderSetReq.put(r.getReminderId(), WebsocUtils.getAllStatus());
            lastDept = r.getDept();
        }

    }

    /**
    * Performs a full scan of Reminders, comparing them to websoc, and
    compiling those with matching status
    */
    // @Scheduled(fixedRate = scanInterval)
    public void fullScan() {
        // List<String> departments = WebsocUtils.getDepartments(url);
        // LOG.debug(departments.toString());
        // for (String dept : departments) {
        // String selectDeptQuery = "SELECT * FROM %s where %s=%s;";
        // List<AccountReminderDto> reminders = null;
        // try {
        // reminders = dbService.customQuery(AccountReminderDto.class,
        // String.format(selectDeptQuery,
        // AccountReminderDto.TABLE_NAME, "dept", StringUtils.join("\'",
        // dept,
        // "\'")));
        // }
        // catch (DatabaseException e) {
        // LOG.error("Could not get reminders", e);
        // }
        //
        // if (reminders != null && !reminders.isEmpty()) {
        // Map<Integer, Set<String>> statusReq = new HashMap<>();
        // for (AccountReminderDto r : reminders) {
        // statusReq.put(r.getCode(), WebsocUtils.getAllStatus());
        // }
        //
        // Document doc = scrapper.searchDept(dept);
        // Map<Integer, String> updates = scrapper.getMatchingClasses(doc,
        // statusReq);
        // if (!updates.isEmpty()) {
        // LOG.debug("Found the following: {}", updates);
        // final List<AccountReminderDto> finalReminders = reminders;
        // executors.submit(() -> {
        // handleCourseChanges(updates, finalReminders);
        // });
        // }
        // }
        // }
    }

    //
    // /**
    // * Loops through the updates, and compiles a list of accounts that need to
    // be notified
    // * @param updates
    // * @param reminders
    // */
    public void handleCourseChanges(String dept, Map<String, String> updates) {
        List<AccountReminderDto> accountsToEmail = accountReminderDao.getAccountsWithDept(dept);
        LOG.info("Emailing {} about {}", accountsToEmail, updates.keySet());
        // Set<UUID> accountsToQuery = new HashSet<>();
        // reminders.stream().map(r ->
        // r.getAccountId()).forEach(accountsToQuery::add);
        // String accountQuery = "SELECT %s FROM %s WHERE %s IN %s;";

        //
        // List<AccountDto> accounts = null;
        // try {
        // accounts = dbService.customQuery(AccountDto.class,
        // String.format(accountQuery, "accountid, email, status",
        // AccountDto.TABLE_NAME, "accountid",
        // DatabaseUtils.setToString(accountsToQuery)));
        // }
        // catch (DatabaseException e) {
        // LOG.error("Could not get accounts", e);
        // }
        //
        // Map<UUID, String> emailMap = getAccountEmails(accounts);
        //
        // Map<RunnerNotification, Set<String>> notifications = new HashMap<>();
        // for (AccountReminderDto acr : reminders) {
        // int code = acr.getCode();
        // String dept = acr.getDept();
        // String number = acr.getNumber();
        // String status = acr.getStatus();
        // if (updates.get(code).equals(status)) {
        // RunnerNotification newValue = new RunnerNotification(dept, number,
        // code, status);
        // if (notifications.containsKey(newValue))
        // notifications.get(newValue).add(emailMap.get(acr.getAccountId()));
        // else {
        // Set<String> newSet = new HashSet<>();
        // newSet.add(emailMap.get(acr.getAccountId()));
        // notifications.put(newValue, newSet);
        // }
        // }
        // NotificationService.sendNotifications(notifications);
        // }
    }

    private static Map<UUID, String> getAccountEmails(List<AccountDto> accounts) {
        Map<UUID, String> result = new HashMap<>();
        for (AccountDto accountDto : accounts) {
            result.put(accountDto.getAccountId(), accountDto.getEmail());
        }
        return result;
    }

    public void sleep() throws InterruptedException {
        LOG.info("Attempting to sleep");
        Thread.sleep(scanInterval);
    }

}
