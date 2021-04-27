package de.lewolf.MOTD;

public class User {

    private final String userName;
    private Message userMessage;

    public User(String userName) {
        this.userName = userName;
        this.userMessage = new Message("Adam goes here");
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
