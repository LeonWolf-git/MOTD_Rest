package de.lewolf.MOTD.models;

import java.time.LocalDate;

public class Message {

    private String messageText;
    private LocalDate dateOfMessage;

    public Message(String messageText, LocalDate dateOfMessage) {
        this.messageText = messageText;
        this.dateOfMessage = dateOfMessage;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public LocalDate getDateOfMessage() {
        return dateOfMessage;
    }

    public void setDateOfMessage(LocalDate dateOfMessage) {
        this.dateOfMessage = dateOfMessage;
    }

}
