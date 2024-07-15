package ru.gb.timesheet.repository;

import org.springframework.stereotype.Repository;
import ru.gb.timesheet.model.Timesheet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository // @Component для классов, работающих с данными
public class TimesheetRepository {

  private static Long sequence = 1L;
  private final List<Timesheet> timesheets = new ArrayList<>();

  public Optional<Timesheet> getById(Long id) {
    return timesheets.stream()
      .filter(it -> Objects.equals(it.getId(), id))
      .findFirst();
  }

  public List<Timesheet> getAll() {
    return List.copyOf(timesheets);
  }

  public Timesheet create(Timesheet timesheet) {
    timesheet.setId(sequence++);
    timesheet.setCreatedAt(LocalDateTime.now());
    timesheets.add(timesheet);
    return timesheet;
  }
  public List<Timesheet> getAllByPeriod(LocalDateTime createdAtBefore, LocalDateTime createdAtAfter) {
    if (Objects.nonNull(createdAtBefore) && Objects.nonNull(createdAtAfter)) {
      return timesheets.stream().filter(u -> u.getCreatedAt().isAfter(createdAtAfter))
              .filter(u -> u.getCreatedAt().isBefore(createdAtBefore)).toList();
    }
    else return getAll();
  }


  public void delete(Long id) {
    timesheets.stream()
      .filter(it -> Objects.equals(it.getId(), id))
      .findFirst()
      .ifPresent(timesheets::remove); // если нет - иногда посылают 404 Not Found
  }


  public List<Timesheet> findByProjectId(Long projectId) {
    return timesheets.stream()
            .filter(it -> Objects.equals(it.getProject().getId(), projectId))
            .toList();
  }

}
