package de.lewolf.MOTD.models;

public class Message {

    private String messageText;

    public Message(String messageText) {
        this.messageText = messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

}
