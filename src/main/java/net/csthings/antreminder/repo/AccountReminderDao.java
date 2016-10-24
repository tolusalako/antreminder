package net.csthings.antreminder.repo;

import java.util.List;
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

    @Query("SELECT a FROM AccountReminderDto a JOIN a.reminders r WHERE r.dept = :dept GROUP BY r.reminderId")
    List<AccountReminderDto> getAccountsWithDept(@Param("dept") String dept);
}
