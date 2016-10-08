package net.csthings.antreminder.entity.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import net.csthings.antreminder.entity.dto.ReminderDto.ReminderPK;

@Data
@Entity
@IdClass(ReminderPK.class)
@Table(name = ReminderAccountDto.TABLE_NAME)
public class ReminderAccountDto {
    public static final String TABLE_NAME = "reminder_accounts";

    @Id
    private String reminderId;
    @Id
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AccountDto.class, cascade = CascadeType.ALL)
    private Set<AccountDto> accounts;

    public ReminderAccountDto() {
        accounts = new HashSet<>();
    }

    public ReminderAccountDto(String reminderId, String status) {
        this();
        this.reminderId = reminderId;
        this.status = status;
    }

}
