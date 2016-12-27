package net.csthings.antreminder.entity.dto;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.csthings.antreminder.services.account.utils.AccountStatus;

/*
 * Created on: Aug 10, 2016
 * 
 * @author Toluwanimi Salako Last edited: Aug 10, 2016
 * 
 * @purpose - TODO
 */

@Entity
@Table(name = AccountDto.TABLE_NAME)
public class AccountDto implements Serializable {
    public static final String TABLE_NAME = "accounts";
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID accountId;
    private String email;

    private String password;
    private int status;
    private Date created;
    private Date online;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ReminderDto.class, cascade = { CascadeType.MERGE })
    @JoinTable(name = AccountReminderDto.TABLE_NAME, joinColumns = { @JoinColumn(name = "accountId") },
        inverseJoinColumns = { @JoinColumn(name = "status"), @JoinColumn(name = "reminderId") })
    private Set<ReminderDto> reminders;

    public AccountDto() {
        this.reminders = new HashSet<>();
    }

    public AccountDto(UUID id) {
        this();
        this.accountId = id;
    }

    public AccountDto(UUID id, String email) {
        this();
        this.accountId = id;
        this.email = email;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getOnline() {
        return online;
    }

    public void setOnline(Date online) {
        this.online = online;
    }

    public Set<ReminderDto> getReminders() {
        return reminders;
    }

    public void setReminders(Set<ReminderDto> reminders) {
        this.reminders = reminders;
    }

    @JsonIgnore
    @Transient
    public static AccountDto createNewAccount(String email, String password) {
        AccountDto result = new AccountDto();
        result.email = email;
        result.password = password;
        result.status = AccountStatus.NEW;
        result.created = new Date();
        return result;
    }
}
