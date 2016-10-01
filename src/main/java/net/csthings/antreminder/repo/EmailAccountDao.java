package net.csthings.antreminder.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.EmailAccountDto;

@Repository
@Transactional
public interface EmailAccountDao extends JpaRepository<EmailAccountDto, String> {

}
