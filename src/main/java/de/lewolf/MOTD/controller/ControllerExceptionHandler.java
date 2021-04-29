package de.lewolf.MOTD.controller;

import de.lewolf.MOTD.exceptions.ServerConnectionException;
import de.lewolf.MOTD.exceptions.UserAlreadyExistsException;
import de.lewolf.MOTD.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = ServerConnectionException.class)
    public ResponseEntity<?> exception(ServerConnectionException exception) {
        return new ResponseEntity<>("Server nicht erreichbar!", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<?> exception(UserAlreadyExistsException exception) {
        return new ResponseEntity<>("Benutzername bereits vergeben!", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<?> exception(UserNotFoundException exception) {
        return new ResponseEntity<>("Benutzer konnte nicht gefunden werden!", HttpStatus.NOT_FOUND);
    }
}
