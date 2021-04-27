package de.lewolf.MOTD;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MotdController {

    private final MotdService service;

    public MotdController(MotdService service) {
        this.service = service;
    }

    @PostMapping("/user/{userName}")
    public ResponseEntity<User> generateNewUser(@PathVariable String userName) {
        return service.generateNewUser(userName);
    }

    @PostMapping(path = "/message", consumes = "application/json")
    public ResponseEntity<Message> createMessage(@RequestBody InputDto dto) {
        return service.setMessage(dto.getUserName(), dto.getMessage());
    }

    @GetMapping(path = "/message/{userName}", produces = "application/json")
    public ResponseEntity<User> getMessage(@PathVariable String userName) {
        return service.getMessage(userName);
    }

}
