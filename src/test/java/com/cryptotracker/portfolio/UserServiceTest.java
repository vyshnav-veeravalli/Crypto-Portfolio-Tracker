package com.cryptotracker.portfolio;

import com.cryptotracker.portfolio.DTO.LoginDTO;
import com.cryptotracker.portfolio.DTO.UserDTO;
import com.cryptotracker.portfolio.entity.User;
import com.cryptotracker.portfolio.repository.UserRepo;
import com.cryptotracker.portfolio.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    void cleanup() {
        userRepo.deleteAll();  // Clean DB before each test
    }

    @Test
    @Order(1)
    void testRegisterUser() {
        UserDTO userDTO = new UserDTO("Alice", "alice@example.com", "pass123", "User");
        User user = userService.register(userDTO);
        assertNotNull(user.getId());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    @Order(2)
    void testLoginSuccess() {
        UserDTO userDTO = new UserDTO("Bob", "bob@example.com", "mypassword", "User");
        userService.register(userDTO);

        LoginDTO loginDTO = new LoginDTO("bob@example.com", "mypassword");
        boolean result = userService.login(loginDTO);

        assertTrue(result);
    }

    @Test
    @Order(3)
    void testLoginFailWrongPassword() {
        UserDTO userDTO = new UserDTO("Charlie", "charlie@example.com", "rightpass", "User");
        userService.register(userDTO);

        LoginDTO loginDTO = new LoginDTO("charlie@example.com", "wrongpass");
        boolean result = userService.login(loginDTO);

        assertFalse(result);
    }

    @Test
    @Order(4)
    void testDuplicateRegistrationThrowsException() {
        UserDTO userDTO = new UserDTO("Dave", "dave@example.com", "pass1", "User");
        userService.register(userDTO);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.register(userDTO); // duplicate email
        });

        assertEquals("Email already exist", exception.getMessage());
    }
}
