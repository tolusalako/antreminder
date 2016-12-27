package net.csthings.antreminder.repo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.AccountDto;

@Repository
@Transactional
public interface AccountDao extends JpaRepository<AccountDto, UUID> {
    @Query("SELECT a.email, r.reminderId, r.title, r.number, r.status FROM AccountDto a JOIN a.reminders r WHERE r.dept = :dept AND r.reminderId IN :reminderId GROUP BY a.accountId, r.reminderId")
    List<String[]> getAccountsWithReminder(@Param("dept") String dept, @Param("reminderId") Set<String> reminderId);
}
