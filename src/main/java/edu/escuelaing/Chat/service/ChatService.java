package edu.escuelaing.Chat.service;

import edu.escuelaing.Chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
/**
 * Service class that handles chat operations such as sending messages and managing connected users.
 * It uses SimpMessagingTemplate to send WebSocket messages to subscribed topics.
 */
@Service
public class ChatService {

    /**
     * Template for sending messages over WebSocket connections.
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * A map that stores connected users with their session ID as the key and username as the value.
     */
    private Map<String, String> users = new HashMap<>();

    /**
     * Sends a message to a specific WebSocket topic.
     *
     * @param topic   The WebSocket topic to send the message to.
     * @param message The Message object to be sent.
     */
    public void sendMessage(String topic, Message message) {
        messagingTemplate.convertAndSend(topic, message);
    }

    /**
     * Adds a new user to the map of connected users.
     *
     * @param sessionId The session ID of the user.
     * @param username  The username of the user.
     */
    public void addUser(String sessionId, String username) {
        users.put(sessionId, username);
        System.out.println("User connected: " + username);
    }

    /**
     * Removes a user from the map of connected users based on their session ID.
     * If the user is found, a message is printed to indicate the disconnection.
     *
     * @param sessionId The session ID of the user to be removed.
     */
    public void removeUser(String sessionId) {
        String username = users.remove(sessionId);
        if (username != null) {
            System.out.println("User disconnected: " + username);
        }
    }

    /**
     * Retrieves the username associated with a given session ID.
     *
     * @param sessionId The session ID of the user.
     * @return The username associated with the session ID, or null if no user is found.
     */
    public String getUsername(String sessionId) {
        return users.get(sessionId);
    }

    /**
     * Broadcasts a message to all connected clients subscribed to the "/topic/messages" topic.
     *
     * @param message The Message object to be broadcasted.
     */
    public void broadcastMessage(Message message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
