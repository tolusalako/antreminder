package net.csthings.antreminder.entity.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = AccountReminderDto.TABLE_NAME)
public class AccountReminderDto {
    public static final String TABLE_NAME = "account_reminders";
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID accountId;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ReminderDto.class, cascade = { CascadeType.MERGE })
    @JoinTable(name = "AnyName", joinColumns = { @JoinColumn(name = "accountId") },
        inverseJoinColumns = { @JoinColumn(name = "reminderId"), @JoinColumn(name = "status") })
    private Set<ReminderDto> reminders;

    public AccountReminderDto() {
        reminders = new HashSet<>();
    }

    public AccountReminderDto(UUID accountId) {
        this();
        this.accountId = accountId;
    }

    public AccountReminderDto(UUID accountId, ReminderDto reminder) {
        this();
        this.accountId = accountId;
        reminders.add(reminder);
    }

}
