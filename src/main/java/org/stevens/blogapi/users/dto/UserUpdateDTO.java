package org.stevens.blogapi.users.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserUpdateDTO {

    String username;

    String email;

    String password;

    String bio;
}
