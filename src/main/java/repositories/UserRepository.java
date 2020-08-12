package repositories;

import dao.UserDAO;
import models.Preference;
import models.User;
import org.apache.commons.dbcp2.BasicDataSource;
import utils.DataBaseUtility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserDAO {

    private static UserRepository userRepository;
    private final BasicDataSource dataSource;

    private UserRepository() throws IOException {
        dataSource = DataBaseUtility.getDataSource();
    }
    public static UserRepository getInstance() throws IOException {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public int createUser(User user) throws SQLException {
        int id;
        String query =
                "insert into users (name, email, password, age, bio, avatar) " +
                        "values (?, ?, crypt(?, gen_salt('bf')) , ?, ?, ?) returning id";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
        ){
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getAge());
            stmt.setString(5, user.getBio());
            stmt.setString(6, user.getAvatar());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            id = rs.getInt("id");
        }
        return id;
    }

    public int deleteUserById(int id) throws SQLException {
        int deleted;
        String query =
                "delete  from users where id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
    }

    public User updateUser(User user) {
        return null;
    }

    public User getUser(User user) {
        return null;
    }

    public User getUserById(int id) throws SQLException {
        User user = null;
        String query =
                "select * from users where id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            user = User.builder().id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .bio(rs.getString("bio"))
                    .email(rs.getString("email"))
                    .avatar(rs.getString("avatar"))
                    .build();
            return user;
        }
    }

    public User loginUser(String email, String password) throws SQLException {
        User user = null;
        String query =
                "select * from users where email = ? and password = crypt(?, password)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query))
        {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                user = User.builder().id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .age(rs.getInt("age"))
                        .bio(rs.getString("bio"))
                        .email(rs.getString("email"))
                        .avatar(rs.getString("avatar"))
                        .build();
            }
            return user;
        }
    }

    @Override
    public List<User> getUserMatchesById(int id) {
        return null;
    }

    @Override
    public List<User> getLikableUsers(int subjectId) throws SQLException {
        List<User> users = new ArrayList<>();
        return null;
    }

    @Override
    public boolean like(int subjectId) throws SQLException {
        return false;
    }

    @Override
    public Preference getUserPreference(int id) throws SQLException {
        return null;
    }
}
