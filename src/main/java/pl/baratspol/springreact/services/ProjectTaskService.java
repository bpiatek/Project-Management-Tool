package pl.baratspol.springreact.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.baratspol.springreact.domain.Backlog;
import pl.baratspol.springreact.domain.Project;
import pl.baratspol.springreact.domain.ProjectTask;
import pl.baratspol.springreact.exceptions.ProjectNotFoundException;
import pl.baratspol.springreact.repositories.BacklogRepository;
import pl.baratspol.springreact.repositories.ProjectRepository;
import pl.baratspol.springreact.repositories.ProjectTaskRepository;

/**
 * Created by Bartosz Piatek on 07/01/2019
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectTaskService {

    private BacklogRepository backlogRepository;
    private ProjectTaskRepository taskRepository;
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getProjectTaskSequence();
            backlog.setProjectTaskSequence(++backlogSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);


            if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if ((projectTask.getStatus().equals("")) || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }
            return taskRepository.save(projectTask);
        } catch (Exception ex) {
            throw new ProjectNotFoundException("Project not found");
        }
    }

    public Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
        if (project == null) {
            throw new ProjectNotFoundException("Project '" + projectIdentifier + "' does not exist");
        }
        return taskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }

    public ProjectTask findByProjectSequence(String projectSequence, String projectTaskSequence) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectSequence);
        if (backlog == null) {
            throw new ProjectNotFoundException("Project '" + projectSequence + "' does not exist");
        }

        ProjectTask projectTask = taskRepository.findByProjectSequence(projectTaskSequence);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task '" + projectTaskSequence + "' not found");
        }

        if (!projectTask.getProjectIdentifier().equals(projectSequence)) {
            throw new ProjectNotFoundException("Project task '" + projectTaskSequence
                    + "' does not exist in project '" + projectSequence);
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String projectSequence, String projectTaskSequence) {
        findByProjectSequence(projectSequence, projectTaskSequence);
        if (updatedTask.getPriority() == 0 || updatedTask.getPriority() == null) {
            updatedTask.setPriority(3);
        }

        if ((updatedTask.getStatus().equals("")) || updatedTask.getStatus() == null) {
            updatedTask.setStatus("TO_DO");
        }
        return taskRepository.save(updatedTask);
    }

    public void deleteProjectTaskByProjectSequence(String projectSequence, String projectTaskSequence) {
        ProjectTask projectTask = findByProjectSequence(projectSequence, projectTaskSequence);
        taskRepository.delete(projectTask);
    }
}
