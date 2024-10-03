package edu.escuelaing.Chat.controller;

import edu.escuelaing.Chat.model.Message;
import edu.escuelaing.Chat.service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        Message message = new Message("john_doe", "Hello World");

        chatController.sendMessage(message);

        verify(chatService, times(1)).broadcastMessage(message);
    }
}
