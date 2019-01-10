package pl.baratspol.springreact.services;

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
public class ProjectTaskService {

    private BacklogRepository backlogRepository;
    private ProjectTaskRepository taskRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectTaskService(BacklogRepository backlogRepository, ProjectTaskRepository taskRepository, ProjectRepository projectRepository) {
        this.backlogRepository = backlogRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            projectTask.setBacklog(backlog);
            Integer backlogSequence = backlog.getProjectTaskSequence();
            backlog.setProjectTaskSequence(++backlogSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if ( projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if (("".equals(projectTask.getStatus())) || projectTask.getStatus() == null) {
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
}
