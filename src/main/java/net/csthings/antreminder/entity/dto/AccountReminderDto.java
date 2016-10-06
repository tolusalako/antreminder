package net.csthings.antreminder.entity.dto;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = AccountReminderDto.TABLE_NAME)
public class AccountReminderDto {
    public static final String TABLE_NAME = "account_reminders";
    @Id
    @Column(name = "accounts_accountid")
    private UUID accountId;
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = ReminderDto.class, cascade = CascadeType.ALL)
    private Set<ReminderDto> reminders;

    public AccountReminderDto() {
    }

}
