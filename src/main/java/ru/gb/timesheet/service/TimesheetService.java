package ru.gb.timesheet.service;

import org.springframework.stereotype.Service;
import ru.gb.timesheet.aspect.Recover;
import ru.gb.timesheet.aspect.Timer;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


@Timer
@Service // то же самое, что и Component
public class TimesheetService {

  private final TimesheetRepository timesheetRepository;
  private final ProjectRepository projectRepository;


  public TimesheetService(TimesheetRepository timesheetRepository, ProjectRepository projectRepository, ProjectService projectService) {
      this.timesheetRepository = timesheetRepository;
      this.projectRepository = projectRepository;

  }
  @Recover
  public Optional<Timesheet> getById(Long id) {
    return timesheetRepository.findById(id);
  }



  public List<Timesheet> findAll(LocalDateTime createdAtBefore, LocalDateTime createdAtAfter) {
    if (createdAtBefore == null || createdAtAfter == null) {
      return timesheetRepository.findAll();
    }
    return timesheetRepository.findByCreatedAtBetween(createdAtBefore, createdAtAfter);
  }



  public Timesheet create(Timesheet timesheet) {
    if (Objects.isNull(timesheet.getProject().getId())) {
      throw new IllegalArgumentException("projectId must not be null");
    }

    if (projectRepository.findById(timesheet.getProject().getId()).isEmpty()) {
      throw new NoSuchElementException("Project with id " + timesheet.getProject().getId() + " does not exists");
    }

    timesheet.setCreatedAt(LocalDateTime.now());
    return timesheetRepository.save(timesheet);
  }

  public void delete(Long id) {
    timesheetRepository.deleteById(id);
  }

}
