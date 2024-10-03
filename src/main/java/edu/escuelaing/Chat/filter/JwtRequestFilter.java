package edu.escuelaing.Chat.filter;

import edu.escuelaing.Chat.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Filter that processes incoming requests to check for a valid JWT token.
 * It ensures that if a valid JWT token is provided, the user is authenticated 
 * for the current request.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Utility class for handling JWT token operations such as extraction and validation.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Service that loads user-specific data for authentication.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Filters incoming requests to validate the JWT token.
     * It extracts the token from the Authorization header, validates it, and sets the 
     * user authentication in the security context if the token is valid.
     *
     * @param request  The HttpServletRequest object that contains the client's request.
     * @param response The HttpServletResponse object that contains the response to the client.
     * @param chain    The FilterChain to pass the request and response to the next filter.
     * @throws ServletException If an error occurs during filtering.
     * @throws IOException If an input or output error occurs during filtering.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if the Authorization header contains a valid JWT token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        // Authenticate the user if a valid JWT token is found
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}