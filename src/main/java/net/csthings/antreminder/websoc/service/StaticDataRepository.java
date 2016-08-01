package net.csthings.antreminder.websoc.service;

import java.util.Collection;

import org.springframework.data.repository.NoRepositoryBean;

import net.csthings.antreminder.websoc.ClassDto;

@NoRepositoryBean
public interface StaticDataRepository { // TODO
                                        // extends
                                        // JpaRepository<ClassDto,
    // UUID>{

    Collection<ClassDto> getAllClasses();
}
