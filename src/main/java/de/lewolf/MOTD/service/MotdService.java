package de.lewolf.MOTD.service;

import de.lewolf.MOTD.models.Message;
import de.lewolf.MOTD.repository.Dao;
import de.lewolf.MOTD.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MotdService {

    private Dao dao;

    public MotdService(Dao dao) {
        this.dao = dao;
    }

    public User generateNewUser(String userName) {
        return dao.insertUser(userName);
    }

    public Message setMessage(String userName, String messageText, LocalDate date) { ;
        return dao.insertMessage(userName, messageText, date);
    }

    public Message getMOTD(String userName) {
        return dao.getMOTD(userName);
    }
}
