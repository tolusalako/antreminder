package net.csthings.antreminder.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;

public class AccountReminderDao {
    @Autowired
    private JdbcTemplate template;

    @Query(nativeQuery = true)
    public void deleteLinkedReminder(String reminderId, String status) {
        template.execute(String.format("DELETE FROM account_reminders where reminderId = %s AND status = %s;",
                reminderId, status));
    }
}
