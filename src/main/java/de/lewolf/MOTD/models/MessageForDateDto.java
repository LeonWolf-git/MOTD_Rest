package de.lewolf.MOTD.models;

import java.time.LocalDate;

public class MessageForDateDto {

    String userName;
    String date;

    public String getUserName() {
        return userName;
    }

    public LocalDate getDate() {return LocalDate.parse(date);}
}
