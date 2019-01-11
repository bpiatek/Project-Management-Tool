package pl.baratspol.springreact.web;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.baratspol.springreact.domain.ProjectTask;
import pl.baratspol.springreact.services.MapValidationErrorService;
import pl.baratspol.springreact.services.ProjectTaskService;

import javax.validation.Valid;
import java.util.Collections;

/**
 * Created by Bartosz Piatek on 07/01/2019
 */
@RestController
@RequestMapping("api/backlog")
@CrossOrigin
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BacklogController {
    private final ProjectTaskService taskService;
    private final MapValidationErrorService validationErrorService;

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

    @GetMapping("{projectSequence}/{projectTaskSequence}")
    public ResponseEntity<?> getProjectTask(
            @PathVariable String projectSequence,
            @PathVariable String projectTaskSequence
    ) {
        ProjectTask projectTask = taskService.findByProjectSequence(projectSequence, projectTaskSequence);
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("{projectSequence}/{projectTaskSequence}")
    public ResponseEntity<?> getProjectTask(
            @Valid @RequestBody ProjectTask updatedTask,
            BindingResult result,
            @PathVariable String projectSequence,
            @PathVariable String projectTaskSequence
    ) {
        ResponseEntity<?> errorMap = validationErrorService.mapValidationError(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask = taskService.updateByProjectSequence(updatedTask, projectSequence, projectTaskSequence);

        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @DeleteMapping("{projectSequence}/{projectTaskSequence}")
    public ResponseEntity<?> deleteProjectTask(
            @PathVariable String projectSequence,
            @PathVariable String projectTaskSequence
    ){
        taskService.deleteProjectTaskByProjectSequence(projectSequence, projectTaskSequence);
        return new ResponseEntity<>(
                Collections.singletonMap("response",
                "Project Task '" + projectTaskSequence + "' was successfully deleted"),
                HttpStatus.OK);
    }
}
