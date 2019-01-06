package pl.baratspol.springreact.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.baratspol.springreact.domain.Project;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

}
