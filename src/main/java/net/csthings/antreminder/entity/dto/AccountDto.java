package net.csthings.antreminder.entity.dto;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import net.csthings.antreminder.services.account.utils.AccountStatus;

/*
 * Created on: Aug 10, 2016
 * 
 * @author Toluwanimi Salako Last edited: Aug 10, 2016
 * 
 * @purpose - TODO
 */

@Data
@Entity
@Table(name = AccountDto.TABLE_NAME)
public class AccountDto {
    public static final String TABLE_NAME = "accounts";
    @Id
    private UUID accountId;
    private String email;
    private String password;
    private int status;
    private Date created;
    private Date online;

    public AccountDto() {
    }

    public AccountDto(UUID id) {
        this.accountId = id;
    }

    @JsonIgnore
    public static AccountDto createNewAccount(String email, String password) {
        AccountDto result = new AccountDto();
        result.email = email;
        result.password = password;
        result.status = AccountStatus.NEW;
        result.created = new Date();
        return result;
    }
}
