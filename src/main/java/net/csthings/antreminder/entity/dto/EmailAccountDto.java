package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = EmailAccountDto.TABLE_NAME)
public class EmailAccountDto {
    public static final String TABLE_NAME = "email_account";
    @Id
    private String email;
    private UUID accountId;

    public EmailAccountDto() {
    }

    public EmailAccountDto(String email, UUID accountid) {
        this.email = email;
        this.accountId = accountid;
    }
}
