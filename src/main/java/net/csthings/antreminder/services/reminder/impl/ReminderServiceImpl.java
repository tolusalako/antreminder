/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package net.csthings.antreminder.services.reminder.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.csthings.antreminder.entity.dto.AccountDto;
import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.entity.dto.ReminderDto.ReminderPK;
import net.csthings.antreminder.repo.AccountDao;
import net.csthings.antreminder.repo.ReminderDao;
import net.csthings.antreminder.services.reminder.ReminderService;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;
import net.csthings.common.utils.CommonError;
import net.csthings.common.utils.CommonUtils;
import net.csthings.common.utils.Status;

public class ReminderServiceImpl implements ReminderService {
    private Logger LOG = LoggerFactory.getLogger(ReminderServiceImpl.class);

    @Autowired
    AccountDao accountDao;

    @Autowired
    ReminderDao reminderDao;

    public ReminderServiceImpl() {
    }

    @Override
    public EmptyResultDto add(UUID accountId, String email, ReminderDto reminder) {
        if (CommonUtils.anyNull(accountId, reminder, reminder.getDept(), reminder.getTitle(),
                reminder.getReminderId())) {
            LOG.debug("Request is invalid");
            return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS, "Request is invalid");
        }

        try {

            AccountDto ar = getAccount(accountId, email);
            ReminderPK reminderKey = new ReminderPK(reminder.getReminderId(), reminder.getStatus());
            ar.getReminders().add(reminder);

            if (!reminderDao.exists(reminderKey))
                reminderDao.save(reminder);
            accountDao.save(ar);
        }
        catch (Exception e) {
            LOG.error("Error processing request", e);
            return new EmptyResultDto(Status.FAILED, CommonError.UNEXPECTED_ERROR, "Error processing reminder");
        }
        return new EmptyResultDto(Status.SUCCESS, null, "Reminder added.");
    }

    @Override
    public ResultDto<Collection<ReminderDto>> get(UUID accountId, String status) {
        if (CommonUtils.isNull(accountId)) {
            LOG.debug("AccountId cannot be null");
            return new ResultDto<>(null, Status.FAILED);
        }
        try {
            AccountDto account = accountDao.findOne(accountId);
            account = account == null ? new AccountDto() : account;
            // TODO cache
            Set<ReminderDto> reminders = account.getReminders();
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
            LOG.error("Error processing request", e);
            return new ResultDto<>(null, Status.FAILED, CommonError.UNEXPECTED_ERROR, "Error processing reminder");
        }
    }

    @Override
    public EmptyResultDto delete(UUID accountId, String email, ReminderDto reminder) {
        if (CommonUtils.anyNull(accountId, reminder, reminder.getStatus(), reminder.getReminderId())) {
            LOG.debug("Request is invalid");
            return new EmptyResultDto(Status.FAILED, CommonError.INVALID_PARAMETERS, "Request is invalid");
        }

        try {

            AccountDto ar = getAccount(accountId, email);
            ar.getReminders().remove(reminder);
            accountDao.save(ar);
            return new EmptyResultDto();
        }
        catch (Exception e) {
            LOG.error("Error processing request", e);
            return new EmptyResultDto(Status.FAILED, CommonError.UNEXPECTED_ERROR, "Error processing reminder");
        }
    }

    private AccountDto getAccount(UUID accountId, String email) {
        AccountDto ar = accountDao.findOne(accountId);
        ar = ar == null ? new AccountDto(accountId, email) : ar;
        return ar;
    }

}
