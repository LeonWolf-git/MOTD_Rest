package de.lewolf.MOTD.service;

import de.lewolf.MOTD.models.Message;
import de.lewolf.MOTD.repository.UsersDaoInterface;
import de.lewolf.MOTD.service.weirdjoke.GetWeirdJokeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MotdService {

    private final UsersDaoInterface dao;
    private final GetWeirdJokeService weirdJokeService;

    public MotdService(UsersDaoInterface dao, GetWeirdJokeService weirdJokeService) {
        this.dao = dao;
        this.weirdJokeService = weirdJokeService;
    }

    public void generateNewUser(String userName) {
        dao.insertUser(userName);
    }

    public void setMessage(String userName, String messageText, LocalDate date) {
        dao.insertMessage(userName, messageText, date);
    }

    public Message getMOTD(String userName) {
        return getMessageForDate(userName, LocalDate.now());
    }

    public Message getMessageForDate(String userName, LocalDate date) {
        return dao.getMessageOfDate(userName, date)
                .orElseGet(() -> weirdJokeService.getRandomWeirdJoke(date));
    }

    public List<Message> getAllMessagesForUser(String userName) {
        return dao.getAllMessages(userName);
    }
}
