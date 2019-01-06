package pl.baratspol.springreact.services;

import com.google.common.collect.Iterables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.baratspol.springreact.domain.Project;
import pl.baratspol.springreact.exceptions.ProjectIdException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
//fresh database before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProjectServiceTest {

    @Autowired
    private ProjectService service;

    private static String PROJECT_IDENTIFIER_1 = "test";
    private static String PROJECT_IDENTIFIER_2 = "test2";
    private static String PROJECT_DESCRIPTION = "desc";

    @BeforeEach
    void setup() {
        Project project = new Project();
        project.setProjectIdentifier(PROJECT_IDENTIFIER_1);
        project.setDescription(PROJECT_DESCRIPTION);
        project.setProjectName("test");

        Project project1 = new Project();
        project1.setProjectIdentifier(PROJECT_IDENTIFIER_2);
        project1.setDescription(PROJECT_DESCRIPTION);
        project1.setProjectName("test");

        Arrays.asList(project, project1)
                .forEach(service::saveOrUpdateProject);
    }

    @Test
    void project_should_be_added_to_database() {
        //given

        //when
        Project projectByIdentifier = service.findByProjectIdentifier(PROJECT_IDENTIFIER_2);
        //then
        assertThat(PROJECT_DESCRIPTION).isEqualTo(projectByIdentifier.getDescription());
    }

    @Test
    void project_should_be_removed_and_throw_exception_when_project_not_found() {
        //given

        //when
        service.deleteProjectByIdentifier(PROJECT_IDENTIFIER_1.toUpperCase());
        //then
        assertThatExceptionOfType(ProjectIdException.class).isThrownBy(() ->
                service.findByProjectIdentifier(PROJECT_IDENTIFIER_1));
    }

    @Test
    void should_find_all_projects() {
        //given

        //when
        Iterable<Project> allProjects = service.findAllProjects();

        //then
        assertThat(2).isEqualTo(Iterables.size(allProjects));
    }
}