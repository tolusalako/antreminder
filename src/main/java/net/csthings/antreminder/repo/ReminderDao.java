package net.csthings.antreminder.repo;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.entity.dto.ReminderDto.ReminderPK;

@Repository
@Transactional
public interface ReminderDao extends JpaRepository<ReminderDto, ReminderPK> {

    @Override
    @CachePut(ReminderDto.TABLE_NAME)
    ReminderDto save(ReminderDto reminder);

    @Override
    @Cacheable(ReminderDto.TABLE_NAME)
    ReminderDto findOne(ReminderPK key);
    //
    // @Query("SELECT r.dept FROM linked_reminders l JOIN reminders r GROUP BY
    // r.dept")
    // Stream<String> getAllDepts();

}
