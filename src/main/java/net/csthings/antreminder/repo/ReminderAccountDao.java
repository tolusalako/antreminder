package net.csthings.antreminder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.csthings.antreminder.entity.dto.ReminderAccountDto;

@Repository
@Transactional
public interface ReminderAccountDao extends JpaRepository<ReminderAccountDto, String> {

}
