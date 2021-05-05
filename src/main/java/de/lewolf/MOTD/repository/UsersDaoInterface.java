package de.lewolf.MOTD.repository;

import de.lewolf.MOTD.models.Message;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsersDaoInterface {
    /*
    - Einen neuen User einfügen
    - Eine neue Message einfügen
    - Eine MOTD eines Users ausgeben
    - Alle Messages eines Users ausgeben
    - Eine Message eines spezifischen Datums eines Users ausgeben
     */

    void insertUser(String username);

    void insertMessage(String username, String message, LocalDate dateOfMessage);

    List<Message> getAllMessages(String username);

    Optional<Message> getMessageOfDate(String username, LocalDate date);

}
