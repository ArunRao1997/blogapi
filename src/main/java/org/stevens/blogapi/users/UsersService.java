package org.stevens.blogapi.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.stevens.blogapi.users.dto.UserUpdateDTO;

import java.util.Optional;
import java.util.UUID;

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

    public UsersEntity getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public UsersEntity getUserById(UUID id) {
        return usersRepository.getUsersById(id);
    }

    public UsersEntity updateUser(UUID id, UserUpdateDTO userUpdateDTO) {
        UsersEntity existingUser = usersRepository.getUsersById(id);

        if (existingUser == null) {
            throw new UserException("User not found"); // Custom exception for user not found
        }

        try {
            if (userUpdateDTO.getUsername() != null) {
                existingUser.setUsername(userUpdateDTO.getUsername());
            }
            if (userUpdateDTO.getEmail() != null) {
                existingUser.setEmail(userUpdateDTO.getEmail());
            }
            if (userUpdateDTO.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
            }
            if (userUpdateDTO.getBio() != null) {
                existingUser.setBio(userUpdateDTO.getBio());
            }

            var updatedUser = usersRepository.save(existingUser);
            return updatedUser;
        } catch (DataAccessException e) {
            throw new UserException("Failed to update user", e);
        }
    }

}
