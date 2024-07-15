package ru.gb.timesheet.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.service.ProjectPageServise;
import ru.gb.timesheet.service.TimesheetPageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Controller
@RequestMapping("/home/projects")
@RequiredArgsConstructor
public class ProjectPageController {

    private final ProjectPageServise projectPageServise;

    @GetMapping
    public String getAllProjects(Model model) {
        List<ProjectPageDto> projects = projectPageServise.findAll();
        model.addAttribute("projects", projects);
        return "projects-page.html";
    }

    // GET /home/projects/{id}
    @GetMapping("/{id}")
    public String getProjectPage(@PathVariable Long id, Model model) {
        Optional<ProjectPageDto> project = projectPageServise.findById(id);
        if (project.isEmpty()) {
            throw new NoSuchElementException();
        }
        model.addAttribute("project", project.get());
        return "project-page.html";
    }

}
