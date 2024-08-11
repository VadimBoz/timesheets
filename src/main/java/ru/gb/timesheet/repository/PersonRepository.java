package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.timesheet.model.Person;

import java.util.Objects;
import java.util.Optional;


public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);

    Optional<Person> findById(Long id);
}

