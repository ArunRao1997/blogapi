package org.stevens.blogapi.security;

import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    /**
     * Creates a JWT or server-side token for the given username
     *
     * @param username
     * @return
     */
    String createAuthToken(String username);

    /**
     * Verifies the given token and returns the username
     *
     * @param token
     * @throws IllegalStateException if token is invalid
     */
    String getUsernameFromToken(String token) throws IllegalStateException;
}
