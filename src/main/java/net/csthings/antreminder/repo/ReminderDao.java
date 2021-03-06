package net.csthings.antreminder.repo;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT r FROM ReminderDto r GROUP BY r.dept, r.reminderId")
    List<ReminderDto> getRemindersGroupDeptId();

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ReminderDto r SET r.emailSent = :newdate WHERE reminderId = :id AND status = :status")
    void updateEmailSent(@Param("id") String reminderId, @Param("status") String status,
            @Param("newdate") Date newDate);

}
