package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

@Entity
@Table(name = ValidationAccountDto.TABLE_NAME)
@Cacheable(ValidationAccountDto.TABLE_NAME)
public class ValidationAccountDto {
    public static final String TABLE_NAME = "validation_account";

    @Id
    private String token;
    @Column(columnDefinition = "BINARY(16)")
    private UUID accountId;

    public ValidationAccountDto() {
    }

    public ValidationAccountDto(UUID accountId, String token) {
        this.accountId = accountId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ValidationAccountDto))
            return false;
        ValidationAccountDto other = (ValidationAccountDto) obj;
        if (accountId == null) {
            if (other.accountId != null)
                return false;
        }
        else if (!accountId.equals(other.accountId))
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        }
        else if (!token.equals(other.token))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ValidationAccountDto [token=" + token + ", accountId=" + accountId + "]";
    }

}