package pl.baratspol.springreact.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.baratspol.springreact.domain.Project;
import pl.baratspol.springreact.services.MapValidationErrorService;
import pl.baratspol.springreact.services.ProjectService;

import javax.validation.Valid;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final MapValidationErrorService validationErrorService;

    @Autowired
    public ProjectController(ProjectService projectService, MapValidationErrorService validationErrorService) {
        this.projectService = projectService;
        this.validationErrorService = validationErrorService;
    }


    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = validationErrorService.mapValidationError(result);
        if(errorMap != null) {
            return errorMap;
        }
        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping("{projectId}")
    public ResponseEntity<?> findProjectByIdentifier(@PathVariable String projectId) {
        Project project = projectService.findByProjectIdentifier(projectId);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("all")
    public Iterable<Project> getPAllProjects() {
        return projectService.findAllProjects();
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<>("Project '" + projectId + "' deleted.", HttpStatus.OK);
    }
}
