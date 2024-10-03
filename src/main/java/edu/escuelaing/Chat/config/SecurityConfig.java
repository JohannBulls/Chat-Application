package edu.escuelaing.Chat.config;

import edu.escuelaing.Chat.filter.JwtRequestFilter;
import edu.escuelaing.Chat.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class configures the security settings for the chat application.
 * It handles user authentication and authorization, password encryption, and JWT filtering.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Filter for handling JWT tokens in requests.
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Spring Security service for retrieving user details.
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Custom service that implements user authentication details.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Bean definition for DaoAuthenticationProvider. This provider authenticates users based on 
     * their username and password stored in a database, using BCrypt for password encoding.
     *
     * @return DaoAuthenticationProvider configured with custom user details and password encoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configures the HTTP security for the application.
     * It disables CSRF protection, allows unauthenticated access to the "/auth/**" endpoint, 
     * and enforces JWT token filtering for other requests.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return SecurityFilterChain which defines security settings for HTTP requests.
     * @throws Exception If there is an error during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean definition for password encoding using BCryptPasswordEncoder.
     * This is used to hash user passwords before storing them in the database.
     *
     * @return PasswordEncoder implementation that uses BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for AuthenticationManager.
     * It retrieves the authentication manager from the provided authentication configuration.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration object.
     * @return AuthenticationManager used to manage authentication operations.
     * @throws Exception If there is an error during the retrieval of the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}