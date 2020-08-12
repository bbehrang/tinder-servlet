package services;

import com.google.gson.Gson;
import models.User;
import repositories.UserRepository;
import utils.JwtUtil;

import java.io.IOException;
import java.sql.SQLException;

public class UserService {
    private static UserService userService;
    private static UserRepository userRepository;

    private UserService() throws IOException {
        userRepository = UserRepository.getInstance();
    }
    public static UserService getInstance() throws IOException {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public int createUser(User user) throws SQLException {
        return userRepository.createUser(user);
    }
    public User loginUser(String email, String password) throws SQLException{
        return userRepository.loginUser(email, password);
    }
    public User getUserById(int id) throws SQLException{
        return userRepository.getUserById(id);
    }
}
