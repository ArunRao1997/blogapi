package org.stevens.blogapi.tokens;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.stevens.blogapi.common.BaseEntity;
import org.stevens.blogapi.users.UsersEntity;

import java.util.Date;

@Entity(name = "user_tokens")
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenEntity extends BaseEntity {

    @ManyToOne
    UsersEntity user;

    Date expiresAt;
}
