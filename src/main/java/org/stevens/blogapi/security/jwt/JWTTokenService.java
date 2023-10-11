package org.stevens.blogapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import org.stevens.blogapi.security.TokenService;

@Service
public class JWTTokenService implements TokenService {

    private final Algorithm algorithm;

    // read this from properties file instead of hardcoding
    private final String SIGNING_KEY = "A long key for signing tokens";
    private final long TOKEN_EXPIRY = 1000 * 60 * 60 * 24; //24hr expiry
    private final String ISSUER = "blog-api";

    public JWTTokenService() {
        this.algorithm = Algorithm.HMAC256(SIGNING_KEY);
    }

    @Override
    public String createAuthToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer(ISSUER)
                .withIssuedAt(new java.util.Date())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + TOKEN_EXPIRY))
                .sign(algorithm);

    }

    @Override
    public String getUsernameFromToken(String token) throws IllegalStateException {
        var verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        var decodedToken = verifier.verify(token);
        return decodedToken.getSubject();
    }
}
