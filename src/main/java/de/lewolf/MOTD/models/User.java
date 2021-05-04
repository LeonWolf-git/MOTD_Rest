package de.lewolf.MOTD.models;

import java.util.List;

public class User {

    private final String userName;
    private Message userMessage;
    private List<Message> allMessagesOfUser;

    public User(String userName) {
        this.userName = userName;
        this.userMessage = null;
    }

    public User(String userName, Message userMessage) {
        this.userName = userName;
        this.userMessage = userMessage;
    }

    public User(String userName, List<Message> allMessagesOfUser) {
        this.userName = userName;
        this.allMessagesOfUser = allMessagesOfUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserMessage(String messageText) {
        userMessage.setMessageText(messageText);
    }

    public Message getUserMessage() {
        return userMessage;
    }

    public List<Message> getAllMessagesOfUser() {
        return allMessagesOfUser;
    }

}
