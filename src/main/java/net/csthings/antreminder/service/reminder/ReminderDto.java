package net.csthings.antreminder.service.reminder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import lombok.Data;

@Entity
public @Data class ReminderDto {
    private String status;
    private String dept;
    private Date expiration;
    private String number;
    private String title;
    private Map<Integer, Boolean> codes;

    // For Thymeleaf
    private String expDate;

    public ReminderDto() {
        codes = new HashMap<>();
    }

}
