package ru.gb.timesheet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@RestController  // -> @Controller + @ResponseBody

@RequestMapping("/timesheets")
public class TimesheetController {

  // GET - получить - не содержит тела
  // POST - create
  // PUT - изменение
  // PATCH - изменение
  // DELETE - удаление

  // @GetMapping("/timesheets/{id}") // получить конкретную запись по идентификатору
  // @DeleteMapping("/timesheets/{id}") // удалить конкретную запись по идентификатору
  // @PutMapping("/timesheets/{id}") // обновить конкретную запись по идентификатору

  private final TimesheetService service;
  private final ProjectController projectController;


  public TimesheetController(TimesheetService service, ProjectController projectController) {
    this.service = service;
    this.projectController = projectController;
  }

  // /timesheets/{id}
  @GetMapping("/{id}") // получить по id
  public ResponseEntity<Timesheet> get(@PathVariable Long id) {
    Optional<Timesheet> ts = service.getById(id);

    if (ts.isPresent()) {
//      return ResponseEntity.ok().body(ts.get());
      return ResponseEntity.status(HttpStatus.OK).body(ts.get());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping // получить все
  public ResponseEntity<List<Timesheet>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @PostMapping // создание нового ресурса
  public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
    timesheet = service.create(timesheet);

    // 201 Created
    return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);

    // 204 No Content
    return ResponseEntity.noContent().build();
  }

}
