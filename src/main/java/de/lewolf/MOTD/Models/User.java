package de.lewolf.MOTD.Models;

public class User {

    private final String userName;
    private Message userMessage;

    public User(String userName) {
        this.userName = userName;
        this.userMessage = new Message("");
    }

    public User(String userName, Message userMessage) {
        this.userName = userName;
        this.userMessage = userMessage;
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

}
