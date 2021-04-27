package de.lewolf.MOTD;

import org.springframework.web.bind.annotation.*;

@RestController
public class MotdController {

    private MotdService service = new MotdService();

    @PostMapping("/userName")
    public @ResponseBody
    User generateNewUser(@RequestParam(value = "userName") String userName) {
        User user = service.generateNewUser(userName);
        return user;
    }

    @PostMapping("/message")
    public @ResponseBody
    Message createMessage(@RequestParam(value = "userName") String userName,
                         @RequestParam(value = "messageText") String message) {
        Message userMessage = service.setMessage(userName, message);
        return userMessage;
    }

    @GetMapping(path = "/getMessage", produces = "application/json")
    public @ResponseBody
    User getMessage(@RequestParam(value = "userName") String userName) {
        return service.getMessage(userName);
    }

}
