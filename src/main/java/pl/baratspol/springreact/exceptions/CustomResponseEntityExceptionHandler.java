package pl.baratspol.springreact.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Bartosz Piatek on 06/01/2019
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException (ProjectIdException ex, WebRequest request) {
        ProjectIdExceprionResponse exceprionResponse = new ProjectIdExceprionResponse(ex.getMessage());
        return new ResponseEntity<>(exceprionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException (ProjectNotFoundException ex, WebRequest request) {
        ProjectNotFoundExceptionResponse exceprionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceprionResponse, HttpStatus.NOT_FOUND);
    }
}
