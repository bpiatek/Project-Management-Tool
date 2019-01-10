package pl.baratspol.springreact.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Bartosz Piatek on 07/01/2019
 */
@Getter
@Setter
public class ProjectNotFoundExceptionResponse {
    private String projectNotFound;

    public ProjectNotFoundExceptionResponse(String projectNotFound) {
        this.projectNotFound = projectNotFound;
    }
}
