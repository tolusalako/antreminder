package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

import lombok.Data;

@Data
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

}