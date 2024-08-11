package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import ru.gb.timesheet.model.Person;
import ru.gb.timesheet.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    List<Role> findPersonById(Long id);
    Optional<Role> findByName(String roleName);

//    @Query(nativeQuery = true, value ="SELECT role FROM Role r WHERE r.personSet.roleSet.id= :roleName")
//    List<Role> findPersonRolesByPersonId(Long personId);
}
