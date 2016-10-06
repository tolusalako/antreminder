package net.csthings.antreminder.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.ReminderAccountDto;

@Repository
@Transactional
public interface ReminderAccountDao extends JpaRepository<ReminderAccountDto, String> {

}
