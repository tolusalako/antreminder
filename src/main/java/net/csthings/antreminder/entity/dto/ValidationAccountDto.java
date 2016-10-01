package net.csthings.antreminder.entity.dto;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = ValidationAccountDto.TABLE_NAME)
public class ValidationAccountDto {
    public static final String TABLE_NAME = "validation_account";

    @Id
    private String token;
    private UUID accountId;

    public ValidationAccountDto() {
    }

    public ValidationAccountDto(UUID accountId, String token) {
        this.accountId = accountId;
        this.token = token;
    }

}