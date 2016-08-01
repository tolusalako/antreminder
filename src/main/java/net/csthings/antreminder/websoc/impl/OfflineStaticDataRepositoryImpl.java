package net.csthings.antreminder.websoc.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.csthings.antreminder.websoc.ClassDto;
import net.csthings.antreminder.websoc.service.StaticDataRepository;

public class OfflineStaticDataRepositoryImpl implements StaticDataRepository {

    @Override
    public Collection<ClassDto> getAllClasses() {
        return generateClasses("Class1", "Class2");
    }

    private static List<ClassDto> generateClasses(String... classes) {
        List<ClassDto> result = new ArrayList<ClassDto>();
        for (String c : classes)
            result.add(new ClassDto(UUID.randomUUID(), c, ""));
        return result;

    }
}