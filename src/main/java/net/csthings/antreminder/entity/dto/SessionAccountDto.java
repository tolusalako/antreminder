package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = SessionAccountDto.TABLE_NAME)
public class SessionAccountDto {
    public static final String TABLE_NAME = "session_account";
    @Id
    String sessionId;
    UUID accountId;

    public SessionAccountDto() {
    }

    public SessionAccountDto(String sessionId, UUID accountId) {
        super();
        this.sessionId = sessionId;
        this.accountId = accountId;
    }
}
