package ru.gb.timesheet.service;

import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Person;

import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.PersonRepository;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final TimesheetRepository timesheetRepository;
    private final ProjectRepository projectRepository;

    public PersonService(PersonRepository personRepository,
                         TimesheetRepository timesheetRepository,
                            ProjectRepository projectRepository) {
        this.personRepository = personRepository;
        this.timesheetRepository = timesheetRepository;
        this.projectRepository = projectRepository;
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

//    public Optional<Project> getByName(String name) {
//        return projectRepository.findByName(name);
//    }

    public List<Person> findAll() {

        return personRepository.findAll();
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public List<Timesheet> getTimesheets(Long idPerson) {
        if (timesheetRepository.findById(idPerson).isEmpty()) {
            throw new NoSuchElementException("Timesheet with idPerson = " + idPerson + " does not exists");
        }
        return timesheetRepository.findAll().stream().
                filter(timesheet -> timesheet.getPersonsList().stream().
                        anyMatch(person -> person.getId().equals(idPerson))).toList();
    }

    public List<Project> getProjects(Long idPerson) {
        if (timesheetRepository.findById(idPerson).isEmpty()) {
            throw new NoSuchElementException("Projects with idPerson = " + idPerson + " does not exists");
        }
        return timesheetRepository.findById(idPerson)
                .map(Timesheet::getProject)
                .stream()
                .distinct()
                .toList();
    }



    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
