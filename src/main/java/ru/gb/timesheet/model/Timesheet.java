package ru.gb.timesheet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
@Table(name = "timesheet")
public class Timesheet {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name  =  "id")
//  @EqualsAndHashCode.Include
  private Long id;

  @ManyToMany(mappedBy = "timesheets", fetch = FetchType.EAGER)
//  @ManyToMany(cascade = CascadeType.ALL)
//  @JoinTable(
//          name = "person_timesheet",
//          joinColumns = { @JoinColumn(name = "timesheet_Id", referencedColumnName="id") },
//          inverseJoinColumns = { @JoinColumn(name = "person_Id", referencedColumnName="id") }
//  )
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name" )
  private Set<Person> personsList = new HashSet<>();


  @ManyToOne
  @JoinColumn(name = "project_id", referencedColumnName = "id")
  private Project project;

  private Integer minutes;
  private LocalDateTime createdAt;

  public Timesheet() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<Person> getPersonsList() {
    return personsList;
  }

  public void setPersonsList(Set<Person> personsList) {
    this.personsList = personsList;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Integer getMinutes() {
    return minutes;
  }

  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void addPerson(Person person) {
    this.personsList.add(person);
  }

  @Override
  public String toString() {
    return "Timesheet{" +
            "id=" + id +
            ", persons=" + personsList.stream().map(u -> u.getName()).toList() +
            ", project=" + project +
            ", minutes=" + minutes +
            ", createdAt=" + createdAt +
            '}';
  }
}
