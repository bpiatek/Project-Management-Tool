package pl.baratspol.springreact.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.baratspol.springreact.domain.ProjectTask;

import java.util.List;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);
    ProjectTask findByProjectSequence(String projectSequence);
}
