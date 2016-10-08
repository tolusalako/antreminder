package net.csthings.antreminder.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.csthings.antreminder.entity.dto.AccountReminderDto;

@Repository
@Transactional
public interface AccountReminderDao extends JpaRepository<AccountReminderDto, UUID> {

}
