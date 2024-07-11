package ru.gb.timesheet.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data

public class Timesheet {

  private Long id;
  private Project project;
  private int minutes;
  private LocalDateTime createdAt;

  public Timesheet() {
  }


}
