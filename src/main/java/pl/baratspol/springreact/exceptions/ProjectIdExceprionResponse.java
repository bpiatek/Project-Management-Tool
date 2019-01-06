package pl.baratspol.springreact.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@Getter @Setter
public class ProjectIdExceprionResponse {
    private String projectIdentifier;

    public ProjectIdExceprionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
