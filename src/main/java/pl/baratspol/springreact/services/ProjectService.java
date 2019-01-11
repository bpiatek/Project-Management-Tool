package pl.baratspol.springreact.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.baratspol.springreact.domain.Backlog;
import pl.baratspol.springreact.domain.Project;
import pl.baratspol.springreact.exceptions.ProjectIdException;
import pl.baratspol.springreact.repositories.BacklogRepository;
import pl.baratspol.springreact.repositories.ProjectRepository;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project Id: " + project.getProjectIdentifier() + " already exists");
        }
    }

    public Project findByProjectIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project Id: '" + projectId + "' does not exists");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Can not delete project '" + projectId + "'. Project does not exists");
        }
        projectRepository.deleteById(project.getId());
    }
}
