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
package net.csthings.antreminder.services.reminder.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.csthings.antreminder.config.RunnerSettings;
import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.repo.AccountDao;
import net.csthings.antreminder.repo.ReminderDao;
import net.csthings.antreminder.services.ServiceException;
import net.csthings.antreminder.services.reminder.util.NotificationService;
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
    private static final long SCAN_INTERVAL = 300000; // 5 min
    public static final long SEND_INTERVAL = 60; // min
    private String url;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RunnerSettings runnerSettings;
    @Autowired
    private ReminderDao reminderDao;
    @Autowired
    private AccountDao accountDao;
    private ExecutorService executors;
    private WebsocScrapper scrapper;

    @PostConstruct
    public void init() {

        executors = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
        url = runnerSettings.getScanUrl();
        scrapper = new WebsocScrapper(url, runnerSettings.getScanExpectedTitle());
    }

    @Scheduled(fixedRate = SCAN_INTERVAL)
    public void reminderScan() {
        List<ReminderDto> reminders = reminderDao.getRemindersGroupDeptId();
        if (reminders.isEmpty())
            return;
        Map<String, Set<String>> reminderSetReq = new HashMap<>();
        String lastDept = reminders.get(0).getDept();
        for (ReminderDto r : reminders) {
            if (null != r.getEmailSent()) {
                long timeDiff = System.currentTimeMillis() - r.getEmailSent().getTime();
                if (r.getEmailSent() != null && timeDiff < TimeUnit.MINUTES.toMillis(SEND_INTERVAL)) {
                    // Don't scan if email has been sent in less than
                    // SEND_INTERVAL
                    LOG.warn("Skipping scan for {}:{}. Last sent {} mins ago.", r.getDept() + " " + r.getNumber(),
                            r.getStatus(), TimeUnit.MILLISECONDS.toMinutes(timeDiff));
                    continue;
                }
            }
            if (!r.getDept().equals(lastDept) && !reminderSetReq.isEmpty()) {
                Map<String, Set<String>> reminderSetReqCopy = new HashMap<>(reminderSetReq);
                reminderSetReq.clear();
                websocScan(lastDept, reminderSetReqCopy);
            }
            reminderSetReq.put(r.getReminderId(), WebsocUtils.getAllStatus());
            lastDept = r.getDept();
        }
        if (!reminderSetReq.isEmpty())
            websocScan(lastDept, reminderSetReq);

    }

    public void websocScan(String dept, final Map<String, Set<String>> reminderSetReqCopy) {
        executors.submit(() -> {
            Document doc = null;
            try {
                doc = scrapper.searchDept(dept);
            }
            catch (ServiceException e) {
                LOG.error("Could not search for dept: {}", dept, e);
            }
            Map<String, String> updates = scrapper.getMatchingClasses(doc, reminderSetReqCopy);
            if (!updates.isEmpty()) {
                LOG.info("Found the following: {}", updates);
                handleCourseChanges(dept, updates);

            }
            else {
                LOG.info("Found no matches for {}", dept);
            }
        });
    }

    /**
    * Loops through the updates and creates notifications that need to be emailed
    * @param updates
    * @param reminders
    */
    public void handleCourseChanges(String dept, Map<String, String> updates) {
        Map<String, List<ReminderDto>> accountsToEmail = compileDataToEmail(dept, updates);
        notificationService.sendNotifications(dept, accountsToEmail);
    }

    /**
     * Compiles a Map(key: email and value: reminders) of what data needs to be included in each email that needs to be sent to users
     * @param dept
     * @param updates
     * @return
     */

    private Map<String, List<ReminderDto>> compileDataToEmail(String dept, Map<String, String> updates) {
        Map<String, List<ReminderDto>> result = new HashMap<>();
        List<String[]> accReminders = accountDao.getAccountsWithReminder(dept, updates.keySet());
        for (Object[] data : accReminders) {
            String status = String.valueOf(data[4]);
            String email = String.valueOf(data[0]);
            String rId = String.valueOf(data[1]);
            String title = String.valueOf(data[2]);
            String number = String.valueOf(data[3]);
            if (!status.equals(updates.get(rId)))
                continue;

            ReminderDto reminder = new ReminderDto(rId, status, title, number);
            List<ReminderDto> reminders = result.get(email);
            if (null == reminders)
                reminders = new ArrayList<>();
            reminders.add(reminder);
            result.put(email, reminders);
        }
        return result;
    }

}
