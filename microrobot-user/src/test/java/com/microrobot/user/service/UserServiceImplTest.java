package com.microrobot.user.service;

import com.microrobot.user.entities.RolUser;
import com.microrobot.user.entities.User;
import com.microrobot.user.exception.entities.EntityNotFoundException;
import com.microrobot.user.persistence.UserRepository;
import com.microrobot.user.security.dto.AuthDTO;
import com.microrobot.user.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                1L,
                "Alejandro Quintero",
                "alejandro@example.com",
                "SecurePass123",
                Set.of(RolUser.EDITOR)
        );
    }

    @Test
    void getUserById_ExistingUser_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void getUserById_NonExistingUser_ThrowsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(99L));
    }

    @Test
    void getAllUsers_ReturnsUserList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
    }

    @Test
    void createUser_SavesUserSuccessfully() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testFindById_UserExists() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFullName("Alejandro Quintero");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userService.findById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Alejandro Quintero", result.getFullName());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_UserNotFound() {
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findById(userId);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
    }




    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFullName("Old Alejandro Quintero");

        User updatedUser = new User();
        updatedUser.setFullName("New Alejandro Quintero");
        updatedUser.setEmail("alejonew@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("New Alejandro Quintero", result.getFullName());
        assertEquals("alejonew@gmail.com", result.getEmail());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        Long userId = 99L;
        when(userRepository.existsById(userId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void testGetUsersByRoles() {
        RolUser role = RolUser.ADMIN;
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findByRoles(role)).thenReturn(mockUsers);

        List<User> result = userService.getUsersByRoles(role);

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findByRoles(role);
    }

    @Test
    void testFindUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "Alejandro Quintero")));

        User user = userService.findById(1L);

        assertNotNull(user);
        assertEquals("Alejandro Quintero", user.getFullName());
        verify(userRepository, times(1)).findById(1L);
    }



    @Test
    void login_Success() {
        AuthDTO authDTO = new AuthDTO();
        User user = new User();
        user.setEmail(authDTO.getEmail());
        user.setPassword(passwordEncoder.encode("password123"));

        when(userRepository.findByEmail(authDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authDTO.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(authDTO.getEmail())).thenReturn("mockToken");

        String token = userService.login(authDTO);

        assertEquals("mockToken", token);
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        AuthDTO authDTO = new AuthDTO("notfound@gmail.com", "password");

        when(userRepository.findByEmail(authDTO.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.login(authDTO));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void login_InvalidPassword_ThrowsException() {
        AuthDTO authDTO = new AuthDTO("invalid@gmail.com", "wrongpassword");
        User user = new User();
        user.setEmail(authDTO.getEmail());
        user.setPassword(passwordEncoder.encode("password123"));

        when(userRepository.findByEmail(authDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authDTO.getPassword(), user.getPassword())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> userService.login(authDTO));

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void register_Success() {
        AuthDTO authDTO = new AuthDTO("test@example.com", "password123");
        User user = new User();

        when(passwordEncoder.encode(authDTO.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userService.register(authDTO);

        assertEquals("User registered successfully!", result);
        verify(userRepository, times(1)).save(any(User.class));
    }


}

