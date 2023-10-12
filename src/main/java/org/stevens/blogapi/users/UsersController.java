package org.stevens.blogapi.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.stevens.blogapi.security.TokenService;
import org.stevens.blogapi.users.dto.CreateUserRequestDTO;
import org.stevens.blogapi.users.dto.LoginUserRequestDTO;
import org.stevens.blogapi.users.dto.UserResponseDTO;
import org.stevens.blogapi.users.dto.UserUpdateDTO;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;

    public UsersController(@Autowired UsersService usersService,
                           @Autowired ModelMapper modelMapper,
                           @Autowired TokenService tokenService
    ) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    ResponseEntity<UserResponseDTO> signupUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        var savedUser = usersService.createUser(
                createUserRequestDTO.getUsername(),
                createUserRequestDTO.getEmail(),
                createUserRequestDTO.getPassword()
        );
        var userResponse = modelMapper.map(savedUser, UserResponseDTO.class);
        userResponse.setToken(tokenService.createAuthToken(savedUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginUserRequestDTO loginUserRequestDTO) {
        var loggedInUser = usersService.LoginUser(
                loginUserRequestDTO.getUsername(),
                loginUserRequestDTO.getPassword()
        );
        var userResponse = modelMapper.map(loggedInUser, UserResponseDTO.class);
        userResponse.setToken(tokenService.createAuthToken(loggedInUser.getUsername()));
        return ResponseEntity.accepted().body(userResponse);
    }

    @GetMapping("/me")
    ResponseEntity<UserResponseDTO> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the Authorization header ("Bearer <token>")
            String token = extractTokenFromHeader(authorizationHeader);

            // JWTTokenService to get the username from the token
            String username = tokenService.getUsernameFromToken(token);

            // Retrieve user details by calling usersService.getUserByUsername(username)
            UsersEntity user = usersService.getUserByUsername(username);

            // Map the user details to a UserResponseDTO
            UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);

            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        // Parsing the Authorization header to extract the token part.
        // If the header is in the format "Bearer <token>",
        // Split it to get the token part.
        String[] parts = authorizationHeader.split(" ");
        if (parts.length == 2) {
            return parts[1];
        }
        throw new IllegalArgumentException("Invalid Authorization header format");
    }


    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID id) {
        UsersEntity user = usersService.getUserById(id);

        if (user != null) {
            // Respond with 200 OK and the user details
            UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
            return ResponseEntity.ok(userResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UsersEntity updatedUser = usersService.updateUser(id,userUpdateDTO);
        UserResponseDTO userResponse = modelMapper.map(updatedUser, UserResponseDTO.class);
        return ResponseEntity.accepted().body(userResponse);
    }

}
