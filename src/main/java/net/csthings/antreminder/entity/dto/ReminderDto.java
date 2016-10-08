package net.csthings.antreminder.entity.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import net.csthings.antreminder.entity.dto.ReminderDto.ReminderPK;

@Data
@Entity
@IdClass(ReminderPK.class)
@Table(name = ReminderDto.TABLE_NAME)
public class ReminderDto implements Serializable {
    public static final String TABLE_NAME = "reminders";

    @Id
    @JsonProperty("code")
    private String reminderId;
    @Id
    private String status;
    private String dept;
    private String number;
    private String title;

    // @ManyToMany(fetch = FetchType.LAZY, targetEntity =
    // AccountReminderDto.class, cascade = CascadeType.ALL)
    // @JoinTable(name = "AnyName", inverseJoinColumns = { @JoinColumn(name =
    // "accountId") },
    // joinColumns = { @JoinColumn(name = "reminderId"), @JoinColumn(name =
    // "status") })
    // Set<AccountReminderDto> accountReminders;

    public ReminderDto() {
    }

    @Data
    @Embeddable
    public static class ReminderPK implements Serializable {

        @Id
        private String reminderId; // Course code
        @Id
        private String status;

        public ReminderPK() {

        }

        public ReminderPK(String reminderId, String status) {
            this.reminderId = reminderId;
            this.status = status;
        }
    }
}
