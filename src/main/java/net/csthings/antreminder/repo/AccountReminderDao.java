package net.csthings.antreminder.repo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.csthings.antreminder.entity.dto.AccountReminderDto;

@Repository
@Transactional
public interface AccountReminderDao extends JpaRepository<AccountReminderDto, UUID> {

    @Query("SELECT a.email, r.reminderId, r.title, r.number FROM AccountReminderDto a JOIN a.reminders r WHERE r.dept = :dept AND r.reminderId IN :reminderId GROUP BY a.accountId, r.reminderId")
    List<String[]> getAccountsWithReminder(@Param("dept") String dept, @Param("reminderId") Set<String> reminderId);
}
