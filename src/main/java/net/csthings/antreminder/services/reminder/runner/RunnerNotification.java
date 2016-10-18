package net.csthings.antreminder.services.reminder.runner;

import lombok.Data;

@Data
public class RunnerNotification {
    private String dept;
    private String number;
    private String status;
    private int code;

    public RunnerNotification(String dept, String number, int code, String status) {
        this.dept = dept;
        this.number = number;
        this.status = status;
        this.code = code;
    }
}
