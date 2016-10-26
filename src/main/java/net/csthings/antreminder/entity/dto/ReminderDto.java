package net.csthings.antreminder.entity.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
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

    public ReminderDto() {
    }

    public ReminderDto(String reminderId, String status, String title, String number) {
        this.reminderId = reminderId;
        this.status = status;
        this.title = title;
        this.number = number;
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

    @Override
    public String toString() {
        return "ReminderDto [reminderId=" + reminderId + ", status=" + status + ", dept=" + dept + ", number=" + number
                + ", title=" + title + "]";
    }
}
