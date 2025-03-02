package com.microrobot.user.service;

import com.microrobot.user.entities.RolUser;
import com.microrobot.user.entities.User;
import com.microrobot.user.exception.entities.EntityNotFoundException;
import com.microrobot.user.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void testFindAll() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        User mockUser = new User();
        mockUser.setFullName("Alejandro Quintero");
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User result = userService.save(mockUser);

        assertNotNull(result);
        assertEquals("Alejandro Quintero", result.getFullName());
        verify(userRepository, times(1)).save(mockUser);
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
}
