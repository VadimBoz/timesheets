package ru.gb.timesheet.service;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Person;
import ru.gb.timesheet.model.Role;
import ru.gb.timesheet.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }


    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }
}
