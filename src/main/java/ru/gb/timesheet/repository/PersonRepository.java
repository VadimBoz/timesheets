package ru.gb.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.timesheet.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long> {

}

