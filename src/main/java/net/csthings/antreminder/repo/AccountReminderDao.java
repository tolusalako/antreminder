package net.csthings.antreminder.repo;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.AccountReminderDto;

@Repository
@Transactional
public interface AccountReminderDao extends JpaRepository<AccountReminderDto, UUID> {

    @Query(value = "SELECT x FROM AccountReminderDto x WHERE x.accountid = ?0")
    List<AccountReminderDto> getRemindersWithID(UUID accountId);

    @Query(value = "SELECT x FROM AccountReminderDto x WHERE x.accountid = ?0 AND x.status = ?1")
    List<AccountReminderDto> getRemindersWithIDAndStatus(UUID accountId, String status);
}
