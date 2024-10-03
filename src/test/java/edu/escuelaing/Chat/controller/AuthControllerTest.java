package edu.escuelaing.Chat.controller;

import edu.escuelaing.Chat.controller.AuthController;
import edu.escuelaing.Chat.model.JwtRequest;
import edu.escuelaing.Chat.service.UserService;
import edu.escuelaing.Chat.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        JwtRequest request = new JwtRequest();
        request.setUsername("john_doe");
        request.setPassword("password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtUtil.generateToken("john_doe")).thenReturn("jwt_token");

        String token = authController.login(request);

        assertNotNull(token);
        assertEquals("jwt_token", token);
        verify(jwtUtil, times(1)).generateToken("john_doe");
    }
}
