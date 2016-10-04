package net.csthings.antreminder.services.reminder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import net.csthings.antreminder.entity.dto.AccountReminderDto;
import net.csthings.antreminder.repo.AccountReminderDao;
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

    public ReminderServiceImpl() {
    }

    @Override
    public EmptyResultDto add(AccountReminderDto accountReminder) {
        if (CommonUtils.anyNull(accountReminder.getDept(), accountReminder.getTitle(), accountReminder.getCode())) {
            log.debug("Request is invalid");
            return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS, "Request is invalid");
        }
        if (accountReminder.getAccountId() == null) {
            log.debug("Reminder must include acccount id");
            return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS,
                    "Reminder must include acccount id");
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
    public ResultDto<AccountReminderDto> get(UUID accountId, String status) { // TODO:
                                                                              // fix
        if (CommonUtils.anyNull(accountId, status)) {
            log.debug("Request is invalid");
            return new ResultDto<AccountReminderDto>(null, Status.FAILED);
        }
        try {
            List<AccountReminderDto> result = accountReminderDao.getRemindersWithIDAndStatus(accountId, status);
            // dbService.customQuery(AccountReminderDto.class,
            // StringUtils.join("SELECT * FROM ", AccountReminderDto.TABLE_NAME,
            // " WHERE accountid = %s AND status = ", status),
            // accountId);
            if (result != null && result.size() > 0)
                return new ResultDto<AccountReminderDto>(result.get(0), Status.SUCCESS);
            else
                return new ResultDto<AccountReminderDto>(null, Status.SUCCESS, null, "Found nothing");
        }
        catch (Exception e) {
            log.error("Error processing request", e);
            return new ResultDto<AccountReminderDto>(null, Status.FAILED, CommonError.UNEXPECTED_ERROR,
                    "Error processing reminder");
        }
    }

    @Override
    public ResultDto<List<AccountReminderDto>> getAll(UUID accountId) {
        if (CommonUtils.anyNull(accountId)) {
            log.debug("Request is invalid");
            return new ResultDto<List<AccountReminderDto>>(null, Status.FAILED);
        }
        try {
            List<UUID> ids = new ArrayList<>();
            ids.add(accountId);
            Iterable<UUID> iterable = ids;
            // List<AccountReminderDto> result =
            // dbService.customQuery(AccountReminderDto.class,
            // StringUtils.join("SELECT * FROM ", AccountReminderDto.TABLE_NAME,
            // " WHERE accountid = %s"),
            // accountId);
            List<AccountReminderDto> result = accountReminderDao.getRemindersWithID(accountId);
            if (result != null && result.size() > 0)
                return new ResultDto<List<AccountReminderDto>>(result, Status.SUCCESS);
            else
                return new ResultDto<List<AccountReminderDto>>(null, Status.SUCCESS, null, "Found nothing");
        }
        catch (Exception e) {
            log.error("Error processing request", e);
            return new ResultDto<List<AccountReminderDto>>(null, Status.FAILED, CommonError.UNEXPECTED_ERROR,
                    "Error processing reminder");
        }
    }

    @Override
    public EmptyResultDto delete(AccountReminderDto reminder) {
        // TODO Auto-generated method stub
        return null;
    }

}
