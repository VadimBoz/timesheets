package ru.gb.timesheet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/projects")
public class ProjectController {

  private final ProjectService projectService;
  private final TimesheetService timesheetService;

  public ProjectController(ProjectService projectService, TimesheetService timesheetService) {

    this.projectService = projectService;
    this.timesheetService = timesheetService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Project> get(@PathVariable Long id) {
    return projectService.findById(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping(value = {"/{id}/timesheets", "/{id}/timesheets/"})
  public ResponseEntity<List<Timesheet>> getTimesheets(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(projectService.getTimesheets(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Project>> getAll() {
    return ResponseEntity.ok(projectService.getAll());
  }

  @PostMapping
  public ResponseEntity<Project> create(@RequestBody Project project) {
    return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(project));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    projectService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
