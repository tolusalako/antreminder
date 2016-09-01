package net.csthings.antreminder.service.reminder;

import java.io.Serializable;

import lombok.Data;

public @Data class EmptyResultDto implements Serializable {
    public final static String FORMAT_LOG = "{} {} {}";
    public final static String FORMAT_IO = "%s, %s, %s";

    private String status;

    private String error;

    private String msg;

    public EmptyResultDto() {
    }

    public EmptyResultDto(String status, String error, String msg) {
        this.status = status;
        this.error = error;
        this.msg = msg;
    }
}
