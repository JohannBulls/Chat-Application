package edu.escuelaing.Chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.escuelaing.Chat.model.User;
import edu.escuelaing.Chat.repository.UserRepository;
/**
 * Service class that implements the UserDetailsService interface for custom user authentication.
 * It retrieves user details from the database and builds a UserDetails object for Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repository used to access user data in the database.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user details based on the provided username.
     * It fetches the user from the database and constructs a UserDetails object for authentication.
     *
     * @param username The username of the user to be authenticated.
     * @return A UserDetails object containing the user's authentication details.
     * @throws UsernameNotFoundException If no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}
