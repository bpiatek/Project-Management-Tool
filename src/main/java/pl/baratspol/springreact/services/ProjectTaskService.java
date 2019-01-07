package pl.baratspol.springreact.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.baratspol.springreact.domain.Backlog;
import pl.baratspol.springreact.domain.ProjectTask;
import pl.baratspol.springreact.repositories.BacklogRepository;
import pl.baratspol.springreact.repositories.ProjectTaskRepository;

import java.util.List;

/**
 * Created by Bartosz Piatek on 07/01/2019
 */
@Service
public class ProjectTaskService {

    private BacklogRepository backlogRepository;
    private ProjectTaskRepository taskRepository;

    @Autowired
    public ProjectTaskService(BacklogRepository backlogRepository, ProjectTaskRepository taskRepository) {
        this.backlogRepository = backlogRepository;
        this.taskRepository = taskRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
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
    }

    public Iterable<ProjectTask> findBacklogByProjectIdentifier(String projectIdentifier) {
        return taskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }
}
