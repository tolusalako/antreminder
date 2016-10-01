package net.csthings.antreminder.repo;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.AccountDto;

@Repository
@Transactional
public interface AccountDao extends JpaRepository<AccountDto, UUID> {

}
