package net.csthings.antreminder.websoc.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import net.csthings.antreminder.websoc.ClassDto;
import net.csthings.antreminder.websoc.service.StaticDataRepository;

public class OfflineStaticDataRepositoryImpl implements StaticDataRepository{
    
    @Override
    public Collection<ClassDto> getAllClasses() {
        return generateClasses("Class1", "Class2");
    }

    private static List<ClassDto> generateClasses(String... classes){
        List<ClassDto> result = new ArrayList<ClassDto>();
        for (String c : classes)
            result.add(new ClassDto(UUID.randomUUID(), c, ""));
        return result;
        
    }

    @Override
    public List<ClassDto> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ClassDto> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ClassDto> findAll(Iterable<UUID> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ClassDto> List<S> save(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <S extends ClassDto> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<ClassDto> entities) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public ClassDto getOne(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ClassDto> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ClassDto> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<ClassDto> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ClassDto> S save(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClassDto findOne(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean exists(UUID id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void delete(UUID id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(ClassDto entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Iterable<? extends ClassDto> entities) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public <S extends ClassDto> S findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ClassDto> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends ClassDto> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends ClassDto> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }
}
