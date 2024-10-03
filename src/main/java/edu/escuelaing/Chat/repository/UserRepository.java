package edu.escuelaing.Chat.repository;

import edu.escuelaing.Chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing user data in the database.
 * It extends JpaRepository to provide basic CRUD operations and custom queries for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Custom method to find a user by their username.
     *
     * @param username The username of the user to be found.
     * @return The User object corresponding to the provided username, or null if no user is found.
     */
    User findByUsername(String username);
}
