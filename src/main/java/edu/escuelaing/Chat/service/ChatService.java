package edu.escuelaing.Chat.service;

import edu.escuelaing.Chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private Map<String, String> users = new HashMap<>();

    public void sendMessage(String topic, Message message) {
        messagingTemplate.convertAndSend(topic, message);
    }

    public void addUser(String sessionId, String username) {
        users.put(sessionId, username);
        System.out.println("Usuario conectado: " + username);
    }

    public void removeUser(String sessionId) {
        String username = users.remove(sessionId);
        if (username != null) {
            System.out.println("Usuario desconectado: " + username);
        }
    }

    public String getUsername(String sessionId) {
        return users.get(sessionId);
    }

    public void broadcastMessage(Message message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
