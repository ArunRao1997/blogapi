package org.stevens.blogapi.users;

import jakarta.persistence.*;
import lombok.*;
import org.stevens.blogapi.articles.ArticlesEntity;
import org.stevens.blogapi.common.BaseEntity;

import java.util.List;


@Getter
@Setter
@ToString
@Entity(name = "users")
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class UsersEntity extends BaseEntity {

    @NonNull
    @Column(nullable = false, unique = true, length = 30)
    String username;

    @NonNull
    @Column(nullable = false, unique = true, length = 50)
    String email;

    @NonNull
    @Column(nullable = false)
    String password;

    @Column
    String bio;

    @ManyToMany(targetEntity = UsersEntity.class, mappedBy = "following")
    List<UsersEntity> followers;

    @ManyToMany
    List<UsersEntity> following;

    @ManyToMany(targetEntity = ArticlesEntity.class, mappedBy = "likes")
    List<ArticlesEntity> favouriteArticles;
}