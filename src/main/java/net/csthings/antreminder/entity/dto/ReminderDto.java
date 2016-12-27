package net.csthings.antreminder.entity.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.csthings.antreminder.entity.dto.ReminderDto.ReminderPK;

@Entity
@IdClass(ReminderPK.class)
@Table(name = ReminderDto.TABLE_NAME, indexes = { @Index(columnList = "dept", name = "reminders_dept_index"),
        @Index(columnList = "status", name = "reminders_status_index") })
@Cacheable
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
    private Date emailSent;

    public ReminderDto() {
    }

    public ReminderDto(String reminderId, String status, String title, String number) {
        this.reminderId = reminderId;
        this.status = status;
        this.title = title;
        this.number = number;
    }

    @Embeddable
    public static class ReminderPK implements Serializable {

        @Id
        private String reminderId;
        @Id
        private String status;

        public ReminderPK() {

        }

        public ReminderPK(String reminderId, String status) {
            this.reminderId = reminderId;
            this.status = status;
        }

        public String getReminderId() {
            return reminderId;
        }

        public void setReminderId(String reminderId) {
            this.reminderId = reminderId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public String getReminderId() {
        return reminderId;
    }

    public void setReminderId(String reminderId) {
        this.reminderId = reminderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Date emailSent) {
        this.emailSent = emailSent;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((reminderId == null) ? 0 : reminderId.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ReminderDto))
            return false;
        ReminderDto other = (ReminderDto) obj;
        if (reminderId == null) {
            if (other.reminderId != null)
                return false;
        }
        else if (!reminderId.equals(other.reminderId))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        }
        else if (!status.equals(other.status))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ReminderDto [reminderId=" + reminderId + ", status=" + status + ", dept=" + dept + ", number=" + number
                + ", title=" + title + "]";
    }
}
