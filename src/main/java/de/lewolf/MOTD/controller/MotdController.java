package de.lewolf.MOTD.controller;

import de.lewolf.MOTD.models.InputDto;
import de.lewolf.MOTD.service.MotdService;
import de.lewolf.MOTD.models.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MotdController {

    private final MotdService service;

    public MotdController(MotdService service) {
        this.service = service;
    }

    @PostMapping("/user/{userName}")
    public ResponseEntity<?> generateNewUser(@PathVariable String userName) {
        User user = service.generateNewUser(userName);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @PostMapping(path = "/message", consumes = "application/json")
    public ResponseEntity<User> createMessage(@RequestBody InputDto dto) {
        User user = service.setMessage(dto.getUserName(), dto.getMessage(), dto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @GetMapping(path = "/message/{userName}", produces = "application/json")
    public ResponseEntity<User> getMessage(@PathVariable String userName) {
        User user = service.getMessage(userName);
        return ResponseEntity.ok()
                .body(user);
    }
}
