package pl.algorit.restfulservices.restutils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OnControllerErrorAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity
                .badRequest()
                .body(Error.builder()
                        .code(e.getClass().getSimpleName())
                        .message(e.getMessage())
                        .build());
    }

}
