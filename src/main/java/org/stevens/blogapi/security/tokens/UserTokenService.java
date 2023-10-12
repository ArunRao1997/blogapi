package org.stevens.blogapi.security.tokens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.stevens.blogapi.security.TokenService;
import org.stevens.blogapi.users.UsersRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class UserTokenService implements TokenService {
    private final UsersRepository usersRepository;
    private final UserTokenRepository userTokenRepository;

    public UserTokenService(
            @Autowired UserTokenRepository userTokenRepository,
            @Autowired UsersRepository usersRepository
    ) {
        this.userTokenRepository = userTokenRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public String createAuthToken(String username) {
        var user = usersRepository.findByUsername(username);
        var token = userTokenRepository.save(UserTokenEntity.builder()
                .user(user)
                .expiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .build()
        );
        return token.getId().toString();
    }

    @Override
    public String getUsernameFromToken(String token) throws IllegalStateException {
        var savedToken = userTokenRepository.findById(UUID.fromString(token));
        if (savedToken.isPresent()) {
            var user = savedToken.get().getUser();

            // Check if the token is expired
            Date expirationDate = savedToken.get().getExpiresAt();
            Date now = new Date();
            if (expirationDate != null && expirationDate.before(now)) {
                // Token has expired
                throw new IllegalStateException("Token has expired");
            }
            if (user != null) {
                return user.getUsername();
            }
        }
        throw new IllegalStateException("Token not found or validation failed");
    }
}
