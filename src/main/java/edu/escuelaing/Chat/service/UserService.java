package edu.escuelaing.Chat.service;

import edu.escuelaing.Chat.model.User;
import edu.escuelaing.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class that handles user management operations such as saving and retrieving users.
 * It interacts with the UserRepository for database operations and uses PasswordEncoder for password encryption.
 */
@Service
public class UserService {

    /**
     * Repository used to access user data in the database.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Encoder for securely hashing user passwords.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Saves a new user to the database after encoding their password.
     *
     * @param user The User object containing the user information to be saved.
     * @return The saved User object.
     */
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves all registered users from the database.
     *
     * @return A list of User objects.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to be found.
     * @return The User object corresponding to the provided username, or null if no user is found.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
