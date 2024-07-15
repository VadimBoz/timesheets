package ru.gb.timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service // то же самое, что и Component
public class TimesheetService {

  private final TimesheetRepository repository;
  private final ProjectRepository projectRepository;
  private final ProjectService projectService;

  public TimesheetService(TimesheetRepository repository, ProjectRepository projectRepository, ProjectService projectService) {
      this.repository = repository;
      this.projectRepository = projectRepository;
      this.projectService = projectService;
  }

  public Optional<Timesheet> getById(Long id) {
    return repository.getById(id);
  }

  public List<Timesheet> getAll() {
    return repository.getAll();
  }
  public List<Timesheet> getAllByPeriod(LocalDateTime createdAtBefore, LocalDateTime createdAtAfter) {
    return repository.getAllByPeriod(createdAtBefore, createdAtAfter);
  }



  public Timesheet create(Timesheet timesheet) {

    Optional<String> projectName = Optional.ofNullable(timesheet.getProject().getName());
    if (projectName.isPresent()) {
      Optional<Project> project = projectRepository.getByName(projectName.get());
      if (project.isPresent()) {
        timesheet.setProject(project.get());
      } else {
        Project newProject = new Project();
        newProject.setName(projectName.get());
        projectService.create(newProject);
        timesheet.setProject(newProject);
      }
      return repository.create(timesheet);

    }
    return null;
  }

  public void delete(Long id) {
    repository.delete(id);
  }

}
