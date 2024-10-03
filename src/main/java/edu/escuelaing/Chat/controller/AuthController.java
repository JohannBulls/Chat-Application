package edu.escuelaing.Chat.controller;

import edu.escuelaing.Chat.model.JwtRequest;
import edu.escuelaing.Chat.service.UserService;
import edu.escuelaing.Chat.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
/**
 * Controller that handles authentication requests for the chat application.
 * It provides an endpoint for users to log in and receive a JWT token upon successful authentication.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * Manages the authentication process using username and password.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Service that handles user-related operations.
     */
    @Autowired
    private UserService userService;

    /**
     * Utility class for generating and validating JWT tokens.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint to authenticate a user and return a JWT token.
     * The user must provide a valid username and password in the request body. 
     * If the authentication is successful, a JWT token is generated and returned.
     *
     * @param request The JwtRequest object containing the username and password.
     * @return A JWT token for the authenticated user.
     * @throws AuthenticationException If the authentication process fails.
     */
    @PostMapping("/login")
    public String login(@RequestBody JwtRequest request) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        return jwtUtil.generateToken(request.getUsername());
    }
}