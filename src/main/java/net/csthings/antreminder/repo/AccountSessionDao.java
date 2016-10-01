package net.csthings.antreminder.repo;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.AccountSessionDto;

@Repository
@Transactional
public interface AccountSessionDao extends JpaRepository<AccountSessionDto, UUID> {

}
