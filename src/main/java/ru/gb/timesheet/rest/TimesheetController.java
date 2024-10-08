package ru.gb.timesheet.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.aspect.Recover;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "timesheets", description = "timesheets")
@RestController
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

  public TimesheetController(TimesheetService service) {
    this.service = service;
  }

  @Operation(summary = "Get timesheets on id", description = "Get timesheetson id ",
          responses = {
          @ApiResponse(description = "успешное выполнение", responseCode = "200", content = @Content(schema = @Schema(implementation = Timesheet.class))),
          @ApiResponse(description = "неудачное выполнение", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
          @ApiResponse(description = "неудачное выполнение", responseCode = "500", content = @Content(schema = @Schema(implementation = Void.class))),
          })
  @Recover
  @GetMapping("/{id}")
  public ResponseEntity<Timesheet> get(@Parameter(description = "id проекта") @PathVariable String id) {
    if (!id.matches("\\d")) throw new IllegalArgumentException();
    Timesheet timesheet = service.getById(Long.parseLong(id))
            .orElseThrow(() -> new NoSuchElementException("элемент не найден"));
    return ResponseEntity.ok(timesheet);
//      .map(ResponseEntity::ok)
//      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // /timesheets
  // /timesheets?createdAtBefore=2024-07-09
  // /timesheets?createdAtAfter=2024-07-15
  // /timesheets?createdAtAfter=2024-07-15&createdAtBefore=2024-06-05
  @Operation(summary = "Get all timesheets", description = "Get all timesheets")
  @GetMapping
  public ResponseEntity<List<Timesheet>> getAll(
    @RequestParam(required = false) LocalDateTime createdAtBefore,
    @RequestParam(required = false) LocalDateTime createdAtAfter
  ) {
    return ResponseEntity.ok(service.findAll(createdAtBefore, createdAtAfter));
  }

  // client -> [spring-server -> ... -> TimesheetController
  //                          -> exceptionHandler(e)
  // client <- [spring-server <- ...

  @PostMapping // создание нового ресурса
  public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
    final Timesheet created = service.create(timesheet);

    // 201 Created
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);

    // 204 No Content
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
    return ResponseEntity.notFound().build();
  }

}
