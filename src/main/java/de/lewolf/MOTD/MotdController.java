package de.lewolf.MOTD;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MotdController {

    private User currentUser;
    private List<User> allUsers = new ArrayList();

    @PostMapping("/userName")
    public HttpStatus generateNewUser(@RequestParam(value = "userName") String userName) {
        currentUser = new User(userName);
        allUsers.add(currentUser);
        return HttpStatus.CREATED;
    }

    @PostMapping("/message")
    public HttpStatus createMessage(@RequestParam(value = "userName") String userName ,
                              @RequestParam(value = "messageText") String message){
        User user = searchUser(userName);
        user.setUserMessage(message);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping(path = "/getMessage", produces = "text/HTML")
    public String getMessage(@RequestParam(value = "userName") String userName){
        User foundUser = searchUser(userName);
        return "" + foundUser.getUserName() + "'s Status lautet: " + foundUser.getUserMessage();
    }

    public User searchUser(String givenUserName){
        for (User user : allUsers) {
            if(user.getUserName().equals(givenUserName)){
                return user;
            }
        }
        return null;
    }
}
