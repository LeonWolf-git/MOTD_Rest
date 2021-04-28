package de.lewolf.MOTD.Service;

import de.lewolf.MOTD.Repository.Dao;
import de.lewolf.MOTD.Models.Message;
import de.lewolf.MOTD.Models.User;
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

    public Message setMessage(String userName, String messageText) { ;
        return dao.insertMessage(userName, messageText);
    }

    public User getMessage(String userName) {
        return dao.getMessage(userName);
    }
}
