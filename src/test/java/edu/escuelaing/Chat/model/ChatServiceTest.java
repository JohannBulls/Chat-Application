package edu.escuelaing.Chat.model;

import edu.escuelaing.Chat.model.Message;
import edu.escuelaing.Chat.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ChatServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        Message message = new Message("john_doe", "Hello world!");

        chatService.sendMessage("/topic/messages", message);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/messages", message);
    }

    @Test
    void testBroadcastMessage() {
        Message message = new Message("john_doe", "Hello everyone!");

        chatService.broadcastMessage(message);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/messages", message);
    }

    @Test
    void testAddUser() {
        chatService.addUser("session1", "john_doe");

        assertEquals("john_doe", chatService.getUsername("session1"));
    }

    @Test
    void testRemoveUser() {
        chatService.addUser("session1", "john_doe");

        chatService.removeUser("session1");

        assertNull(chatService.getUsername("session1"));
    }
}
