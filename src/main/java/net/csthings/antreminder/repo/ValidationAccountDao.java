package net.csthings.antreminder.repo;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.ValidationAccountDto;

@Repository
@Transactional
@CacheConfig(cacheNames = ValidationAccountDto.TABLE_NAME)
public interface ValidationAccountDao extends JpaRepository<ValidationAccountDto, String> {

}
