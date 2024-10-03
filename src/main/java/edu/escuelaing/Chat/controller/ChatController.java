package edu.escuelaing.Chat.controller;

import edu.escuelaing.Chat.model.Message;
import edu.escuelaing.Chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Controller that handles WebSocket messaging for the chat application.
 * It listens for incoming chat messages and broadcasts them to all connected clients.
 */
@Controller
public class ChatController {

    /**
     * Service responsible for handling chat operations such as broadcasting messages.
     */
    @Autowired
    private ChatService chatService;

    /**
     * Handles incoming messages from the WebSocket endpoint.
     * It listens to the "/chat" endpoint, processes the message, and broadcasts it to 
     * all subscribers of the "/topic/messages" topic.
     *
     * @param message The Message object received from a client.
     * @return The same Message object, which is then sent to all connected clients.
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message) {
        chatService.broadcastMessage(message);
        return message;
    }
}