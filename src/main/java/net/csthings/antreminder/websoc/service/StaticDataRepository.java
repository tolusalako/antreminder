package net.csthings.antreminder.websoc.service;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import net.csthings.antreminder.websoc.ClassDto;

@NoRepositoryBean
public interface StaticDataRepository extends JpaRepository<ClassDto, UUID>{
    
    Collection<ClassDto> getAllClasses();
}
