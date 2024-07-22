package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.page.ProjectPageDto;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProjectPageServise {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public List<ProjectPageDto> findAll() {
        return projectService.getAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<ProjectPageDto> findById(Long id) {
        return projectService.findById(id).map(this::convert);
    }

    private ProjectPageDto convert(Project project) {
        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setProjectName(project.getName());
        projectPageDto.setProjectId(String.valueOf(project.getId()));
        return projectPageDto;
    }

}
