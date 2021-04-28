package de.lewolf.MOTD.service;

import de.lewolf.MOTD.repository.Dao;
import de.lewolf.MOTD.models.User;
import org.springframework.stereotype.Service;

@Service
public class MotdService {

    private Dao dao;

    public MotdService(Dao dao) {
        this.dao = dao;
    }

    public User generateNewUser(String userName) {
        return dao.insertUser(userName);
    }

    public User setMessage(String userName, String messageText) { ;
        return dao.insertMessage(userName, messageText);
    }

    public User getMessage(String userName) {
        return dao.getUser(userName);
    }
}
