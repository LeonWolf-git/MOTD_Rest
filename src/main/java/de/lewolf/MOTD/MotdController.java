package de.lewolf.MOTD;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MotdController {

    private User currentUser;
    private List<User> allUsers = new ArrayList();

    @PostMapping("/userName")
    public void generateNewUser(@RequestBody String userName) {
        currentUser = new User(userName);
        allUsers.add(currentUser);
    }

    @PostMapping("/message")
    public void createMessage(@RequestBody String message){
        currentUser.setUserMessage(message);
    }

    @GetMapping(path = "/getMessage", produces = "application/json")
    public String getMessage(@RequestBody String userName){
        User foundUser = searchUser(userName);
        return foundUser.getUserMessage();
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
