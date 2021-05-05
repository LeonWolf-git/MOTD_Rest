package de.lewolf.MOTD.repository;

import de.lewolf.MOTD.exceptions.MessageNotFoundException;
import de.lewolf.MOTD.exceptions.UserAlreadyExistsException;
import de.lewolf.MOTD.exceptions.UserNotFoundException;
import de.lewolf.MOTD.models.Message;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersDao implements UsersDaoInterface {


    private final JdbcTemplate jdbcTemplate;

    public UsersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Todo: Befüllen
    @Override
    public void insertUser(String username) {
        try {
            jdbcTemplate.update("INSERT INTO userdb.users (username) VALUES (?)",
                    (preparedStatement) -> preparedStatement.setString(1, username));
        } catch (DataAccessException e) {
            throw new UserAlreadyExistsException("Der User: " + username + " existiert bereits!");
        }
    }

    @Override
    public void insertMessage(String username, String message, LocalDate dateOfMessage) {
        try {
            jdbcTemplate.update("INSERT INTO userdb.messages (username, message, dateOfMessage) VALUES (?, ?, ?)",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, username);
                            preparedStatement.setString(2, message);
                            preparedStatement.setDate(3, Date.valueOf(dateOfMessage));
                        }
                    });
        } catch (DataAccessException e) {
            throw new UserNotFoundException("Der User: " + username + " existiert nicht!");
        }

    }

    @Override
    public List<Message> getAllMessages(String username) {
        try {
            List<Message> allMessages = jdbcTemplate.query(
                    "SELECT * FROM userdb.messages WHERE username=?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, username);
                        }
                    },
                    new RowMapper<Message>() {
                        public Message mapRow(ResultSet rs, int rn) throws SQLException {
                            Message m = new Message(rs.getString(2), rs.getDate(3).toLocalDate());
                            return m;
                        }
                    }
            );
            if (allMessages.isEmpty()) {
                throw new MessageNotFoundException("Nix da!");
            }
            return allMessages;
        } catch (DataAccessException | MessageNotFoundException e) {
            throw new UserNotFoundException("Der User: " + username + " existiert nicht, oder hat keine Messages!");
        }
    }

    @Override
    public Optional<Message> getMessageOfDate(String username, LocalDate date) {
        return Optional.empty();
    }
}
