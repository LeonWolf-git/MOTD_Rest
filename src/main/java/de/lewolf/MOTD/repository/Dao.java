package de.lewolf.MOTD.repository;

import de.lewolf.MOTD.exceptions.ServerConnectionException;
import de.lewolf.MOTD.exceptions.UserAlreadyExistsException;
import de.lewolf.MOTD.exceptions.UserNotFoundException;
import de.lewolf.MOTD.models.Message;
import de.lewolf.MOTD.models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class Dao {

    private final String url = "jdbc:mysql://localhost:3306/userdb";

    public User insertUser(String userName) {
        String query = "INSERT INTO userdb.users (username, message) VALUES (?, ?)";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, userName);
            stmnt.setString(2, null);
            stmnt.executeUpdate();
            return getUser(userName);
        } catch (SQLException e) {
            throw new UserAlreadyExistsException(e);
        }
    }

    public User insertMessage(String username, String message) {
        String query = "UPDATE userdb.users SET message=? WHERE username=?";
        try (Connection connection = establishConnection();
             PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, message);
            stmnt.setString(2, username);
            stmnt.executeUpdate();
            return getUser(username);
        } catch (SQLException e) {
            throw new UserNotFoundException(e);
        }
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
