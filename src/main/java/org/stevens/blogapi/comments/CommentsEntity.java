package org.stevens.blogapi.comments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.stevens.blogapi.articles.ArticlesEntity;
import org.stevens.blogapi.common.BaseEntity;
import org.stevens.blogapi.users.UsersEntity;

@Getter
@Entity(name = "comments")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CommentsEntity extends BaseEntity {

    @Column(nullable = false)
    String title;

    @Column(nullable = false, length = 1000)
    String body;

    @ManyToOne
    UsersEntity author;

    @ManyToOne
    ArticlesEntity articles;
}
