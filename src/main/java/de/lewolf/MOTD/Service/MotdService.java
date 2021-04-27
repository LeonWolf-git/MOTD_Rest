package de.lewolf.MOTD.Service;

import de.lewolf.MOTD.Models.Message;
import de.lewolf.MOTD.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MotdService {

    private List<User> allUsers = new ArrayList();

    // Todo: return objects

    public User generateNewUser(String userName) {
        if (!userAlreadyExists(userName)) {
            User generatedUser = new User(userName);
            allUsers.add(generatedUser);
            return generatedUser;
        } else {
            return null;
        }
    }

    public Message setMessage(String userName, String messageText) {
        User foundUser = searchUser(userName);
        if (foundUser != null) {
            foundUser.setUserMessage(messageText);
            return foundUser.getUserMessage();
        } else {
            return null;
        }
    }

    public User getMessage(String userName) {
        User foundUser = searchUser(userName);
        if (foundUser != null) {
            return foundUser;
        } else {
            return null;
        }
    }

    public User searchUser(String givenUserName) {
        for (User user : allUsers) {
            if (user.getUserName().equals(givenUserName)) {
                return user;
            }
        }
        return null;
    }

    public boolean userAlreadyExists(String userName) {
        for (User user : allUsers) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
