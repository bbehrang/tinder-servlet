package dao;

import models.Preference;
import models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    int createUser(User user) throws SQLException;
    int deleteUserById(int id) throws SQLException;
    User updateUser(User user) throws SQLException;
    User getUser(User user) throws SQLException;
    User getUserById(int id) throws SQLException;
    List<User> getUserMatchesById(int id) throws SQLException;
    List<User> getLikableUsers(int subjectId) throws SQLException;
    boolean like(int subjectId) throws SQLException;
    Preference getUserPreference(int id) throws SQLException;
}
