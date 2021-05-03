package de.lewolf.MOTD.service;

import de.lewolf.MOTD.models.Message;
import de.lewolf.MOTD.models.MessageForDateDto;
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

    public void generateNewUser(String userName) {
        dao.insertUser(userName);
    }

    public void setMessage(String userName, String messageText, LocalDate date) { ;
        dao.insertMessage(userName, messageText, date);
    }

    public String getMOTD(String userName) {
        return dao.getMOTD(userName);
    }

    public String getMessageForDate(String userName, LocalDate date){
        return dao.getMessageForDate(userName, date);
    }
}
