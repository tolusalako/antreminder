package net.csthings.antreminder.entity.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

//
@Data
@Entity
@Table(name = ReminderAccountDto.TABLE_NAME)
public class ReminderAccountDto {
    public static final String TABLE_NAME = "reminder_accounts";
    //
    @Id
    // @PrimaryKeyJoinColumn
    private String dept;
    // @Id
    // @PrimaryKeyJoinColumn
    // private String reminderId;
    // @Id
    // @PrimaryKeyJoinColumn
    // private String status;
    //
    // @ManyToMany(fetch = FetchType.LAZY, targetEntity = AccountDto.class,
    // cascade = { CascadeType.MERGE })
    // @JoinTable(name = ReminderAccountDto.TABLE_NAME, inverseJoinColumns = {
    // @JoinColumn(name = "accountId") },
    // joinColumns = { @JoinColumn(name = "status"), @JoinColumn(name =
    // "reminderId") })
    // private Set<AccountDto> accounts;
    //
    // public ReminderAccountDto() {
    // // accounts = new HashSet<>();
    // }
    //
    // //
    // public ReminderAccountDto(String dept, String reminderId, String status)
    // {
    // this();
    // this.dept = dept;
    // this.reminderId = reminderId;
    // this.status = status;
    // }
    //
}
