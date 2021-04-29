package de.lewolf.MOTD.repository;

import de.lewolf.MOTD.exceptions.ServerConnectionException;
import de.lewolf.MOTD.exceptions.UserAlreadyExistsException;
import de.lewolf.MOTD.exceptions.UserNotFoundException;
import de.lewolf.MOTD.models.Message;
import de.lewolf.MOTD.models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;

@Repository
public class Dao {

    // Todo: wenn ich als user den service aufrufe, möchte ich meine message von heute bekommen
    // Todo: Optional Message von anderem Rest-Service aufrufen

    private final String url = "jdbc:mysql://localhost:3306/userdb";

    public User insertUser(String userName) {
        String query = "INSERT INTO userdb.users (username, message) VALUES (?, ?)";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, userName);
            stmnt.setString(2, null);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            throw new UserAlreadyExistsException(e);
        }
        return getUser(userName);
    }

    public User insertMessage(String username, String message, LocalDate date) {
        String query = "INSERT INTO userdb.messages (username, message, dateOfMessage) VALUES (?, ?, ?)";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            stmnt.setString(2, message);
            stmnt.setDate(3, Date.valueOf(date));
            stmnt.executeUpdate();
        } catch (SQLException e) {
            throw new UserNotFoundException(e);
        }
        return getUser(username);
    }

    public User getUser(String username) {
        String query = "SELECT * FROM userdb.users WHERE username=?";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            try (ResultSet resultSet = stmnt.executeQuery()) {
                resultSet.next();
                return new User(resultSet.getString(1), new Message(resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new UserNotFoundException(e);
        }
    }

    private Connection establishConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, "root", "user");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServerConnectionException(e);
        }
    }
}
