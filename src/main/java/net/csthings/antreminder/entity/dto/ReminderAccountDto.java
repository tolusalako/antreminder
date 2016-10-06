package net.csthings.antreminder.entity.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = ReminderAccountDto.TABLE_NAME)
public class ReminderAccountDto {
    public static final String TABLE_NAME = "reminder_accounts";

    @Id
    @Column(name = "reminders_reminderid")
    private String reminderId;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = AccountDto.class, cascade = CascadeType.ALL)
    private Set<AccountDto> account;

    public ReminderAccountDto() {
    }

}
