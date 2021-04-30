package de.lewolf.MOTD.controller;

import de.lewolf.MOTD.models.InputDto;
import de.lewolf.MOTD.models.Message;
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
    public ResponseEntity<Message> createMessage(@RequestBody InputDto dto) {
        Message message = service.setMessage(dto.getUserName(), dto.getMessage(), dto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(message);
    }

    @GetMapping(path = "/message/{userName}", produces = "application/json")
    public ResponseEntity<Message> getMOTD(@PathVariable String userName) {
        Message message = service.getMOTD(userName);
        return ResponseEntity.ok()
                .body(message);
    }
}
