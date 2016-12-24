package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = SessionAccountDto.TABLE_NAME)
public class SessionAccountDto {
    public static final String TABLE_NAME = "session_account";
    @Id
    String sessionId;
    @Column(columnDefinition = "BINARY(16)")
    UUID accountId;

    public SessionAccountDto() {
    }

    public SessionAccountDto(String sessionId, UUID accountId) {
        super();
        this.sessionId = sessionId;
        this.accountId = accountId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
        result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SessionAccountDto))
            return false;
        SessionAccountDto other = (SessionAccountDto) obj;
        if (accountId == null) {
            if (other.accountId != null)
                return false;
        }
        else if (!accountId.equals(other.accountId))
            return false;
        if (sessionId == null) {
            if (other.sessionId != null)
                return false;
        }
        else if (!sessionId.equals(other.sessionId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SessionAccountDto [sessionId=" + sessionId + ", accountId=" + accountId + "]";
    }

}
