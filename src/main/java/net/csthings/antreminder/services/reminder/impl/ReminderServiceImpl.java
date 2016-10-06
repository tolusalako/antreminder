package net.csthings.antreminder.services.reminder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import net.csthings.antreminder.entity.dto.AccountReminderDto;
import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.repo.AccountReminderDao;
import net.csthings.antreminder.repo.ReminderAccountDao;
import net.csthings.antreminder.services.reminder.ReminderService;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;
import net.csthings.common.utils.CommonError;
import net.csthings.common.utils.CommonUtils;
import net.csthings.common.utils.Status;

@Slf4j
public class ReminderServiceImpl implements ReminderService {

    // private static final int TIME_TO_LIVE = 1209600; // 2 weeks

    @Autowired
    AccountReminderDao accountReminderDao;

    @Autowired
    ReminderDao reminderDao;

    @Autowired
    ReminderAccountDao reminderAccountDao;

    public ReminderServiceImpl() {
    }

    @Override
    public EmptyResultDto add(UUID accountId, ReminderDto reminder) {
        if (CommonUtils.anyNull(accountId, reminder, reminder.getDept(), reminder.getTitle(),
                reminder.getReminderId())) {
            log.debug("Request is invalid");
            return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS, "Request is invalid");
        }

        try {
            // Save to account_reminders
            accountReminderDao.save(accountReminder);
        }
        catch (Exception e) {
            log.error("Error processing request", e);
            log.debug("{}", accountReminder.toString());
            return new EmptyResultDto(Status.FAILED, CommonError.UNEXPECTED_ERROR, "Error processing reminder");
        }
        return new EmptyResultDto(Status.SUCCESS, null, "Reminder added.");
    }

    @Override
    public ResultDto<Collection<ReminderDto>> get(UUID accountId, String status) {
        if (CommonUtils.isNull(accountId)) {
            log.debug("AccountId cannot be null");
            return new ResultDto<>(null, Status.FAILED);
        }
        try {
            AccountReminderDto accountReminder = accountReminderDao.findOne(accountId);
            // TODO cache
            Set<ReminderDto> reminders = accountReminder.getReminders();
            if (StringUtils.isEmpty(status))
                return new ResultDto<>(reminders, Status.SUCCESS);

            List<ReminderDto> result = new ArrayList<>();
            reminders.stream().filter(r -> r.getStatus().equals(status)).forEach(result::add);

            if (!result.isEmpty())
                return new ResultDto<>(result, Status.SUCCESS);
            else
                return new ResultDto<>(null, Status.SUCCESS, null, "Found nothing");
        }
        catch (HibernateException e) {
            log.error("Error processing request", e);
            return new ResultDto<>(null, Status.FAILED, CommonError.UNEXPECTED_ERROR, "Error processing reminder");
        }
    }

    @Override
    public EmptyResultDto delete(AccountReminderDto reminder) {
        // TODO Auto-generated method stub
        return null;
    }

}
