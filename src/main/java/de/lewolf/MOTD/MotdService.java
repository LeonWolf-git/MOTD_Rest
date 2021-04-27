package de.lewolf.MOTD;

import java.util.ArrayList;
import java.util.List;

public class MotdService {

    private List<User> allUsers = new ArrayList();

    public User generateNewUser(String userName) {
        User generatedUser = new User(userName);
        allUsers.add(generatedUser);
        return generatedUser;
    }

    public Message setMessage(String userName, String messageText) {
        User user = searchUser(userName);
        user.setUserMessage(messageText);
        return user.getUserMessage();
    }

    public User getMessage(String userName) {
        return searchUser(userName);
    }

    public User searchUser(String givenUserName) {
        for (User user : allUsers) {
            if (user.getUserName().equals(givenUserName)) {
                return user;
            }
        }
        return null;
    }
}
