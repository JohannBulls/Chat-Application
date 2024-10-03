package edu.escuelaing.Chat.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for handling operations related to JWT (JSON Web Tokens).
 * It provides methods to generate, validate, and extract information from JWT tokens.
 */
@Component
public class JwtUtil {

    /**
     * Secret key used for signing and validating the JWT tokens.
     */
    private String SECRET_KEY = "miClaveSecretaMuySegura12345";

    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username (subject) stored in the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claim resolver function.
     *
     * @param token          The JWT token.
     * @param claimsResolver The function used to resolve the specific claim.
     * @param <T>            The type of the claim to be extracted.
     * @return The value of the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return A Claims object containing all claims from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Checks whether the JWT token has expired.
     *
     * @param token The JWT token.
     * @return True if the token has expired, otherwise false.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a new JWT token for a given username.
     *
     * @param username The username for which the token is generated.
     * @return The generated JWT token.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Creates a new JWT token with the specified claims and subject (username).
     *
     * @param claims  The claims to be included in the token.
     * @param subject The subject (username) for the token.
     * @return The generated JWT token.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token expires in 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Validates the JWT token by checking the username and expiration date.
     *
     * @param token    The JWT token to be validated.
     * @param username The username to validate against the token.
     * @return True if the token is valid and not expired, otherwise false.
     */
    public Boolean validateToken(String token, String username) {
        final String usernameExtracted = extractUsername(token);
        return (usernameExtracted.equals(username) && !isTokenExpired(token));
    }
}
