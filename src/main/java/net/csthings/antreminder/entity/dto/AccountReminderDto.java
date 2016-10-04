package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@IdClass(AccountReminderPK.class)
@Table(name = AccountReminderDto.TABLE_NAME)
public class AccountReminderDto {
    public static final String TABLE_NAME = "account_reminders";
    @EmbeddedId
    AccountReminderPK key;
    @Id
    private UUID accountId;
    @Id
    private int code;
    @Id
    private String status;
    private String dept;
    private String number;
    private String title;

    public AccountReminderDto() {
    }

}
