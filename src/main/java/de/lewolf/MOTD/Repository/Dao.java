package de.lewolf.MOTD.Repository;

import de.lewolf.MOTD.Models.Message;
import de.lewolf.MOTD.Models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class Dao {

    private final String url = "jdbc:mysql://localhost:3306/userdb";

    public User insertUser(String userName) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, "root", "user");
            String query = "INSERT INTO userdb.users (username, message) VALUES (?, ?)";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, userName);
            stmnt.setString(2, null);
            stmnt.executeUpdate();
            return new User(userName);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Message insertMessage(String username, String message) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, "root", "user");
            String query = "UPDATE userdb.users SET message=? WHERE username=?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, message);
            stmnt.setString(2, username);
            stmnt.executeUpdate();
            return new Message(message);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getMessage(String username) {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, "root", "user");
            String query = "SELECT * FROM userdb.users WHERE username=?";
            PreparedStatement stmnt = connection.prepareStatement(query);
            stmnt.setString(1, username);
            ResultSet resultSet = stmnt.executeQuery();
            resultSet.next();
            return new User(resultSet.getString(1), new Message(resultSet.getString(2)));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
