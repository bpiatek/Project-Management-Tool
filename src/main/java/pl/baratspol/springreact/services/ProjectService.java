package pl.baratspol.springreact.services;

import org.springframework.stereotype.Service;
import pl.baratspol.springreact.domain.Project;
import pl.baratspol.springreact.exceptions.ProjectIdException;
import pl.baratspol.springreact.repositories.ProjectRepository;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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
