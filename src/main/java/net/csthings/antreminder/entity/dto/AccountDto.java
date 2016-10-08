package net.csthings.antreminder.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
public class AccountDto implements Serializable {
    public static final String TABLE_NAME = "accounts";
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID accountId;
    private String email;
    private String password;
    private int status;
    private Date created;
    private Date online;

    public AccountDto() {
    }

    public AccountDto(UUID id) {
        this();
        this.accountId = id;
    }

    @JsonIgnore
    @Transient
    public static AccountDto createNewAccount(String email, String password) {
        AccountDto result = new AccountDto();
        result.email = email;
        result.password = password;
        result.status = AccountStatus.NEW;
        result.created = new Date();
        return result;
    }
}
