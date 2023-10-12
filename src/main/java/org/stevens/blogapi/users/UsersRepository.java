package org.stevens.blogapi.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, UUID> {
    UsersEntity findByUsername(String username);

    UsersEntity findByEmail(String email);
    UsersEntity getUsersById(UUID id);
}
