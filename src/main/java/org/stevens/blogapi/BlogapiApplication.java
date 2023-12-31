package org.stevens.blogapi;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.stevens.blogapi.security.jwt.JWTTokenService;
import org.stevens.blogapi.security.TokenService;
import org.stevens.blogapi.security.tokens.UserTokenRepository;
import org.stevens.blogapi.security.tokens.UserTokenService;
import org.stevens.blogapi.users.UsersRepository;

@SpringBootApplication
public class BlogapiApplication {

    public static void main(String[] args) {

        SpringApplication.run(BlogapiApplication.class, args);
    }

    private final static String TOKEN_SERVICE_TYPE = "JWT"; //SST OR JWT

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        return mapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * This is how we migrate from Bcrypt to Argon2
         */
        var bcryptEncoder = new BCryptPasswordEncoder();
        var argon2Encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {

                return argon2Encoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                var passMatch = argon2Encoder.matches(rawPassword, encodedPassword);
                if (!passMatch) {
                    passMatch = bcryptEncoder.matches(rawPassword, encodedPassword);
                }
                return passMatch;
            }
        };
    }

    @Bean()
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public TokenService tokenService(
            @Autowired UserTokenRepository userTokenRepository,
            @Autowired UsersRepository usersRepository
    ) {
        return switch (TOKEN_SERVICE_TYPE) {
            case "JWT" -> new JWTTokenService();
            case "SST" -> new UserTokenService(userTokenRepository, usersRepository);
            default -> throw new IllegalStateException("Unexpected value: " + TOKEN_SERVICE_TYPE);
        };
    }
}
