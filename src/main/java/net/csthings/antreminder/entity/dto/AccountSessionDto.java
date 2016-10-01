package net.csthings.antreminder.entity.dto;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = AccountSessionDto.TABLE_NAME)
public class AccountSessionDto {
    public static final String TABLE_NAME = "account_session";
    @Id
    UUID accountId;
    String sessionId;
    Date expiration;
    String ip;

    public AccountSessionDto() {

    }

}
