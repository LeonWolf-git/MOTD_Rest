package de.lewolf.MOTD.models;

import java.time.LocalDate;

public class InputDto {

    String userName;
    String message;
    String date;

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getDate() {return LocalDate.parse(date);}
}
