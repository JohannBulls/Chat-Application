package edu.escuelaing.Chat.repository;

import edu.escuelaing.Chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}