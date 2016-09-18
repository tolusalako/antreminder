package net.csthings.antreminder.service.reminder;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class ReminderDto {
    private String status;
    private String dept;
    private String number;
    private String title;
    private String code;

    public ReminderDto() {
    }

}
