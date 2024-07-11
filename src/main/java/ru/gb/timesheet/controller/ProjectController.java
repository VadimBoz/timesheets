package ru.gb.timesheet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final TimesheetService timesheetService;

    public ProjectController(ProjectService projectService, TimesheetService timesheetService) {
        this.projectService = projectService;
        this.timesheetService = timesheetService;
    }

    @GetMapping("/{id}") // получит project по id
    public ResponseEntity<Project> get(@PathVariable Long id) {
        Optional<Project> projects = projectService.getById(id);

        if (projects.isPresent()) {
//      return ResponseEntity.ok().body(ts.get());
            return ResponseEntity.status(HttpStatus.OK).body(projects.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping // получить все
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @PostMapping // создание нового ресурса
    public ResponseEntity<Project> create(@RequestBody Project project) {
        project = projectService.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        System.out.println(id);
        boolean isPresentTimesheet = timesheetService.getAll().stream().anyMatch(u -> u.getProject().getId() == id);
        if (!isPresentTimesheet) {
            projectService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("project  is present in timesheets", HttpStatus.CONFLICT);
    }

}

