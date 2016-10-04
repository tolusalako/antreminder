package net.csthings.antreminder.entity.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class AccountReminderPK implements Serializable {

    private UUID accountId;

    private int code;

    private String status;

    public AccountReminderPK() {

    }

}
