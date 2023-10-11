package org.stevens.blogapi.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UsersService(@Autowired UsersRepository usersRepository,
                        @Autowired ModelMapper modelMapper,
                        @Autowired PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersEntity createUser(String username, String email, String password) {
        var savedUser = usersRepository.save(UsersEntity.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build()
        );
        return savedUser;
    }

    public UsersEntity LoginUser(String username, String password) {
        var loggedInUser = usersRepository.findByUsername(username);
        if (loggedInUser != null) {
            if (passwordEncoder.matches(password, loggedInUser.getPassword())) {
                return loggedInUser;
            }
        }
        throw new IllegalArgumentException("Invalid username or password");
    }
}
