package pl.baratspol.springreact.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.baratspol.springreact.domain.Project;
import pl.baratspol.springreact.repositories.ProjectRepository;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdateProject(Project project) {
        return projectRepository.save(project);
    }

}
