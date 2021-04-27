package de.lewolf.MOTD.Controller;

import de.lewolf.MOTD.Models.InputDto;
import de.lewolf.MOTD.Models.Message;
import de.lewolf.MOTD.Service.MotdService;
import de.lewolf.MOTD.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MotdController {

    private final MotdService service;

    public MotdController(MotdService service) {
        this.service = service;
    }

    // Todo: MySQL holen --> erstellen --> anbinden per JDBC

    @PostMapping("/user/{userName}")
    public ResponseEntity<User> generateNewUser(@PathVariable String userName) {
        User user = service.generateNewUser(userName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PostMapping(path = "/message", consumes = "application/json")
    public ResponseEntity<Message> createMessage(@RequestBody InputDto dto) {
        Message message = service.setMessage(dto.getUserName(), dto.getMessage());
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/message/{userName}", produces = "application/json")
    public ResponseEntity<User> getMessage(@PathVariable String userName) {
        User user = service.getMessage(userName);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
