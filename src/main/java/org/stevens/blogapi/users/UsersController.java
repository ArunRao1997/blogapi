package org.stevens.blogapi.users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stevens.blogapi.security.TokenService;
import org.stevens.blogapi.users.dto.CreateUserRequestDTO;
import org.stevens.blogapi.users.dto.LoginUserRequestDTO;
import org.stevens.blogapi.users.dto.UserResponseDTO;

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
    ResponseEntity<UserResponseDTO> signupUser(@RequestBody CreateUserRequestDTO createUserRequestDTO){
        var savedUser = usersService.createUser(
                createUserRequestDTO.getUsername(),
                createUserRequestDTO.getEmail(),
                createUserRequestDTO.getPassword()
        );
        var userResponse = modelMapper.map(savedUser, UserResponseDTO.class);
        return ResponseEntity.accepted().body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginUserRequestDTO loginUserRequestDTO){
        var loggedInUser = usersService.LoginUser(
                loginUserRequestDTO.getUsername(),
                loginUserRequestDTO.getPassword()
        );
        var userResponse = modelMapper.map(loggedInUser, UserResponseDTO.class);
        return ResponseEntity.accepted().body(userResponse);
    }

    @PatchMapping("/{id}")
    ResponseEntity<UserResponseDTO> updateUser(){

        return null;
    }

}
