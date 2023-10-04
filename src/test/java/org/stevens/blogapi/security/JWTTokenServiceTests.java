package org.stevens.blogapi.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class JWTTokenServiceTests {
    private final JWTTokenService jwtTokenService = new JWTTokenService();
    @Test
    public void testCreateAuthToken(){
        String username = "ArunRao";
        String token = jwtTokenService.createAuthToken(username);
        System.out.println(token);
        Assertions.assertNotNull(token);
    }

    @Test
    public void testTokenVerification(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBcnVuUmFvIiwiaXNzIjoiYmxvZy1hcGkiLCJpYXQiOjE2OTYzNjA5MDgsImV4cCI6MTY5NjQ0NzMwOH0.ZaLNLeaAAdWai_lsVBJiA6L1Onn8bukWuBW39Fo1ZZk";
        String username = jwtTokenService.getUsernameFromToken(token);
        Assertions.assertEquals("ArunRao",username);
    }
}
