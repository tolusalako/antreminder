package net.csthings.antreminder.repo;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.EmailAccountDto;

@Repository
@Transactional
@CacheConfig(cacheNames = EmailAccountDto.TABLE_NAME)
public interface EmailAccountDao extends JpaRepository<EmailAccountDto, String> {

}
