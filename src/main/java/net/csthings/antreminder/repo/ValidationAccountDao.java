package net.csthings.antreminder.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.csthings.antreminder.entity.dto.ValidationAccountDto;

@Repository
@Transactional
public interface ValidationAccountDao extends JpaRepository<ValidationAccountDto, String> {

}
