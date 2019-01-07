package pl.baratspol.springreact.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.baratspol.springreact.domain.ProjectTask;
import pl.baratspol.springreact.services.MapValidationErrorService;
import pl.baratspol.springreact.services.ProjectTaskService;

import javax.validation.Valid;

/**
 * Created by Bartosz Piatek on 07/01/2019
 */
@RestController
@RequestMapping("api/backlog")
@CrossOrigin
public class BacklogController {
    private final ProjectTaskService taskService;
    private final MapValidationErrorService validationErrorService;

    @Autowired
    public BacklogController(ProjectTaskService taskService, MapValidationErrorService validationErrorService) {
        this.taskService = taskService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping("{projectIdentifier}")
    public ResponseEntity<?> addProjectTaskToBAcklog(
            @Valid @RequestBody ProjectTask projectTask,
            BindingResult result,
            @PathVariable String projectIdentifier) {
        ResponseEntity<?> errorMap = validationErrorService.mapValidationError(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask1 = taskService.addProjectTask(projectIdentifier, projectTask);

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("{projectIdentifier}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String projectIdentifier) {
        return taskService.findBacklogByProjectIdentifier(projectIdentifier);
    }
}
