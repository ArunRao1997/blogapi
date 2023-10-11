package org.stevens.blogapi.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UsersRepositoryTests {
    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void createUser() {
        UsersEntity usersEntity = UsersEntity.builder()
                .username("Arun")
                .email("rao.nayienii@gmail.com")
                .password("Pass@123456")
                .build();

        var user = usersRepository.save(usersEntity);
        //System.out.println(user.toString());
        Assertions.assertNotNull(user.getId());
    }

    @Test
    public void findByUsername() {
        UsersEntity usersEntity1 = new UsersEntity(
                "ArunRao",
                "rao.nayineni@gmail.com",
                "Pass123"
        );
        UsersEntity usersEntity2 = new UsersEntity(
                "Harish",
                "harish.nayineni@gmail.com",
                "Pass321"
        );
        usersRepository.save(usersEntity1);
        usersRepository.save(usersEntity2);

        var user1 = usersRepository.findByUsername("ArunRao");
        System.out.println(user1.email);
        var user2 = usersRepository.findByUsername("Harish");

        Assertions.assertEquals("rao.nayineni@gmail.com", user1.getEmail());
        Assertions.assertEquals("harish.nayineni@gmail.com", user2.getEmail());

    }
}
