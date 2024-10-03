package edu.escuelaing.Chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the chat application.
 * It is the entry point for starting the Spring Boot application.
 */
@SpringBootApplication
public class ChatApplication {

    /**
     * Main method that starts the chat application.
     * It uses SpringApplication.run() to bootstrap the Spring Boot application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
