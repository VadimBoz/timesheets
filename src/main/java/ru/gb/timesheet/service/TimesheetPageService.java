package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Person;
import ru.gb.timesheet.page.TimesheetPageDto;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

  private final TimesheetService timesheetService;
  private final ProjectService projectService;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm:ss");

  public List<TimesheetPageDto> findAll() {
    return timesheetService.findAll(null, null).stream()
      .map(this::convert)
      .toList();
  }

  public Optional<TimesheetPageDto> findById(Long id) {
    return timesheetService.getById(id) // Optional<Timesheet>
      .map(this::convert);
  }

  private TimesheetPageDto convert(Timesheet timesheet) {
    Project project = projectService.findById(timesheet.getProject().getId())
      .orElseThrow();

    TimesheetPageDto timesheetPageParameters = new TimesheetPageDto();
    timesheetPageParameters.setProjectName(project.getName());
    String listPerson = timesheet.getPersonsList().stream().map(Person::getName).toList().toString();
    timesheetPageParameters.setListPersonName(listPerson);
    timesheetPageParameters.setProjectId(String.valueOf(project.getId()));
    timesheetPageParameters.setId(String.valueOf(timesheet.getId()));
    timesheetPageParameters.setMinutes(String.valueOf(timesheet.getMinutes()));
    timesheetPageParameters.setCreatedAt(timesheet.getCreatedAt().format(formatter));

    return timesheetPageParameters;
  }

}
