package de.lewolf.MOTD.service;

import de.lewolf.MOTD.repository.Dao;
import de.lewolf.MOTD.models.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

    public User setMessage(String userName, String messageText, LocalDate date) { ;
        return dao.insertMessage(userName, messageText, date);
    }

    public User getMessage(String userName) {
        return dao.getUser(userName);
    }
}
