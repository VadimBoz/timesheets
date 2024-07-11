package ru.gb.timesheet.service;


import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<Project> getById(Long id) {
        return projectRepository.getById(id);
    }
    public Optional<Project> getByName(String name) {
        return projectRepository.getByName(name);
    }
    public List<Project> getAll() {
        return projectRepository.getAll();
    }

    public Project create(Project project) {
        return projectRepository.create(project);
    }

    public void delete(Long id) {
        projectRepository.delete(id);
    }

}

