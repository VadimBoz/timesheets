package ru.gb.timesheet.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name  =  "id")
    private Long id;
    private String name;
    private String surname;
    private String post;
    private String password;

//    @ManyToMany(mappedBy = "persons", fetch = FetchType.EAGER)
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_timesheet",
            joinColumns = { @JoinColumn(name = "person_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "timesheet_id", referencedColumnName="id") }
    )
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
    private Set<Timesheet> timesheets = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_role",
            joinColumns = { @JoinColumn(name = "person_id", referencedColumnName="id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName="id") }
    )
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
    private Set<Role> roleSet = new HashSet<>();


    public Set<Role> getRoles() {
        return roleSet;
    }

    public void setRoles(Set<Role> rolesSet) {
        this.roleSet = roleSet;
    }

    public void addRole(Role role) {
        this.roleSet.add(role);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void addTimesheet(Timesheet timesheet) {
        this.timesheets.add(timesheet);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Set<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(Set<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", post='" + post + '\'' +
                ", timesheets=" + timesheets +
                ", rolesSet=" + roleSet +
                '}';
    }
}
