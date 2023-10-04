package org.stevens.blogapi.articles;

import jakarta.persistence.*;
import lombok.*;
import org.stevens.blogapi.common.BaseEntity;
import org.stevens.blogapi.users.UsersEntity;

import java.util.List;

@Getter
@Entity(name = "articles")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ArticlesEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false,length = 150)
    String title;

    @NonNull
    @Column(nullable = false,length = 100)
    String slug;

    @Column(length = 250)
    String subtitle;

    @NonNull
    @Column(nullable = false,length = 3000)
    String body;

//    @Column
//    String[] tags;

    @NonNull
    @ManyToOne
    UsersEntity author;

    @ManyToMany(targetEntity = UsersEntity.class)
    @JoinTable(
            name = "article_likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<UsersEntity> likes;
}
