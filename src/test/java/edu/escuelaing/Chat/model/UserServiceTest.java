package edu.escuelaing.Chat.model;

import edu.escuelaing.Chat.model.User;
import edu.escuelaing.Chat.repository.UserRepository;
import edu.escuelaing.Chat.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        User user = new User("john_doe", "password123");
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("encoded_password", savedUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(new User("john_doe", "password123"), new User("jane_doe", "password456"));
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("john_doe", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByUsername() {
        User user = new User("john_doe", "password123");
        when(userRepository.findByUsername("john_doe")).thenReturn(user);

        User result = userService.findByUsername("john_doe");

        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        verify(userRepository, times(1)).findByUsername("john_doe");
    }
}
