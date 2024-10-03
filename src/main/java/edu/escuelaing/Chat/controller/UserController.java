package edu.escuelaing.Chat.controller;

import edu.escuelaing.Chat.model.User;
import edu.escuelaing.Chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles user-related operations for the chat application.
 * It provides endpoints to register a new user and retrieve a list of all users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * Service responsible for user management operations such as saving and retrieving users.
     */
    @Autowired
    private UserService userService;

    /**
     * Endpoint to register a new user.
     * It accepts a User object in the request body and saves it in the database.
     *
     * @param user The User object containing user information to be registered.
     * @return The saved User object.
     */
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    /**
     * Endpoint to retrieve all registered users.
     * It returns a list of all users stored in the database.
     *
     * @return A list of User objects.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
