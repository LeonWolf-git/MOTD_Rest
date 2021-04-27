package de.lewolf.MOTD;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MotdService {

    private List<User> allUsers = new ArrayList();

    public ResponseEntity<User> generateNewUser(String userName) {
        if (!userAlreadyExists(userName)) {
            User generatedUser = new User(userName);
            allUsers.add(generatedUser);
            return new ResponseEntity<>(generatedUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Message> setMessage(String userName, String messageText) {
        User foundUser = searchUser(userName);
        if (foundUser != null) {
            foundUser.setUserMessage(messageText);
            return new ResponseEntity<>(foundUser.getUserMessage(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> getMessage(String userName) {
        User foundUser = searchUser(userName);
        if (foundUser != null) {
            return new ResponseEntity<>(foundUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
