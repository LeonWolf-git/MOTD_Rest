package de.lewolf.MOTD.controller;

import de.lewolf.MOTD.models.InputMessageDto;
import de.lewolf.MOTD.models.Message;
import de.lewolf.MOTD.models.MessageForDateDto;
import de.lewolf.MOTD.service.MotdService;
import de.lewolf.MOTD.models.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
public class MotdController {

    private final MotdService service;

    public MotdController(MotdService service) {
        this.service = service;
    }

    @PostMapping("/user/{userName}")
    public ResponseEntity<?> generateNewUser(@PathVariable String userName) {
        service.generateNewUser(userName);
        User user = new User(userName);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(path = "/message", consumes = "application/json")
    public ResponseEntity<Message> createMessage(@RequestBody InputMessageDto dto) {
        service.setMessage(dto.getUserName(), dto.getMessage(), dto.getDate());
        Message message = new Message(dto.getMessage(), dto.getDate());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(message);
    }

    @GetMapping(path= "/messageForDate", consumes = "application/json")
    public ResponseEntity<User> getMessageForDate(@RequestBody MessageForDateDto dto){
        String message = service.getMessageForDate(dto.getUserName(), dto.getDate());
        Message messageForDate = new Message(message, dto.getDate());
        User user = new User(dto.getUserName(), messageForDate);
        return ResponseEntity.ok()
                .body(user);
    }

    // Todo: Erhalte alle Einträge (als Liste) für den User als weitere Funktion --> Rückgabe User-Objekt

    @GetMapping(path = "/message/{userName}", produces = "application/json")
    public ResponseEntity<User> getMOTD(@PathVariable String userName) {
        String messageText = service.getMOTD(userName);
        Message motd = new Message(messageText, LocalDate.now());
        User user = new User(userName, motd);
        return ResponseEntity.ok()
                .body(user);
    }
}
