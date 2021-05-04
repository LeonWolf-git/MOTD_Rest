package de.lewolf.MOTD.repository;

import de.lewolf.MOTD.exceptions.*;
import de.lewolf.MOTD.models.Message;
import de.lewolf.MOTD.models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class Dao {

    private final String url = "jdbc:mysql://localhost:3306/userdb";

    public void insertUser(String username) {
        String query = "INSERT INTO userdb.users (username, message) VALUES (?, ?)";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            stmnt.setString(2, null);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            throw new UserAlreadyExistsException("Der User: " + username + " existiert bereits!");
        }
    }

    public void insertMessage(String username, String message, LocalDate date) {
        getUser(username);
        String query = "INSERT INTO userdb.messages (username, message, dateOfMessage) VALUES (?, ?, ?)";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            stmnt.setString(2, message);
            stmnt.setDate(3, Date.valueOf(date));
            stmnt.executeUpdate();
        } catch (SQLException e) {
            throw new UserNotFoundException("Der User: " + username + " existiert nicht!");
        }
    }

    public String getMessageForDate(String userName, LocalDate date) {
        getUser(userName);
        Optional<Message> optionalMessage = getMessage(userName, date);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            return message.getMessageText();
        } else {
            throw new MessageNotFoundException("Es konnte keine Message für diesen Tag gefunden werden!");
        }
    }


    public User getUser(String username) {
        String query = "SELECT * FROM userdb.users WHERE username=?";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            try (ResultSet resultSet = stmnt.executeQuery()) {
                while (resultSet.next()) {
                    return new User(resultSet.getString("username"));
                }
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Der User: " + username + " konnte nicht gefunden werden!");
        }
    }


    public Optional<Message> getMessage(String username, LocalDate date) {
        String query = "SELECT * FROM userdb.messages WHERE username=? AND dateOfMessage=?";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            stmnt.setDate(2, Date.valueOf(date));
            try (ResultSet resultSet = stmnt.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message(resultSet.getString("message"), resultSet.getDate("dateOfMessage").toLocalDate());
                    return Optional.of(message);
                }
                throw new SQLException();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public String getMOTD(String username) {
        setMOTD(username);
        String query = "SELECT message FROM userdb.users WHERE username=?";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            try (ResultSet resultSet = stmnt.executeQuery()) {
                resultSet.next();
                return resultSet.getString("message");
            }
        } catch (SQLException e) {
            throw new MessageNotFoundException("Die gesuchte Nachricht konnte nicht gefunden wurden");
        }
    }

    public void setMOTD(String username) {
        Optional<Message> optionalMessage = getMessage(username, LocalDate.now());
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            String query = "UPDATE userdb.users SET message=? WHERE username=?";
            try (Connection connection = establishConnection();
                 PreparedStatement stmnt = connection.prepareStatement(query)) {
                stmnt.setString(1, message.getMessageText());
                stmnt.setString(2, username);
                stmnt.executeUpdate();
            } catch (SQLException e) {
                throw new SomethingWentTerriblyWrongException(
                        "Something in your request destroyed us. We don't even know what you did, and we also can't offer any solution at the moment. Pray!");
            }
        } else {
            throw new MessageNotFoundException("Es konnte keine Nachricht fuer den User: " + username + " gefunden werden!");
        }
    }

    public List<Message> getAllMessagesForUser(String username) {
        getUser(username);
        List<Message> allMessageTexts = new ArrayList<>();
        String query = "SELECT * FROM userdb.messages WHERE username=?";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            try (ResultSet resultSet = stmnt.executeQuery()) {
                while (resultSet.next()) {
                    allMessageTexts.add(new Message(resultSet.getString("message"),
                            resultSet.getDate("dateOfMessage").toLocalDate()));
                }
                return allMessageTexts;
            }
        } catch (Exception e) {
            throw new SomethingWentTerriblyWrongException("Pray to god. He is thy only hope left now!");
        }
    }

    private Connection establishConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, "root", "user");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServerConnectionException("Server ded");
        }
    }
}
