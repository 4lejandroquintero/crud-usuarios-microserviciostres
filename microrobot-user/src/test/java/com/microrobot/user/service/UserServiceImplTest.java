package com.microservice.user.service;

import com.microservice.user.entities.RolUser;
import com.microservice.user.entities.User;
import com.microservice.user.exception.entities.EntityNotFoundException;
import com.microservice.user.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository; // Simula el UserRepository

    @InjectMocks
    private UserServiceImpl userService; // Inyecta el mock en UserService

    @Test
    void testFindById_UserExists() {
        // 1. Configura el comportamiento del mock
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFullName("John Doe");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // 2. Ejecuta el método a probar
        User result = userService.findById(userId);

        // 3. Verifica el resultado
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("John Doe", result.getFullName());

        // 4. Verifica que el método del repositorio fue llamado
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_UserNotFound() {
        // 1. Configura el comportamiento del mock para lanzar una excepción
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // 2. Ejecuta el método a probar y verifica que lanza la excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.findById(userId);
        });

        // 3. Verifica el mensaje de la excepción
        assertEquals("User not found with id: " + userId, exception.getMessage());

        // 4. Verifica que el método del repositorio fue llamado
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindAll() {
        // Configura el mock
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Ejecuta el método
        List<User> result = userService.findAll();

        // Verifica el resultado
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        // Configura el mock
        User mockUser = new User();
        mockUser.setFullName("John Doe");
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // Ejecuta el método
        User result = userService.save(mockUser);

        // Verifica el resultado
        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    void testUpdateUser() {
        // Configura el mock
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFullName("Old Name");

        User updatedUser = new User();
        updatedUser.setFullName("New Name");
        updatedUser.setEmail("new@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Ejecuta el método
        User result = userService.updateUser(userId, updatedUser);

        // Verifica el resultado
        assertNotNull(result);
        assertEquals("New Name", result.getFullName());
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser() {
        // Configura el mock
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // Ejecuta el método
        userService.deleteUser(userId);

        // Verifica que el método fue llamado
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Configura el mock para lanzar una excepción
        Long userId = 99L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Verifica que se lanza la excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.deleteUser(userId);
        });

        // Verifica el mensaje de la excepción
        assertEquals("User not found with id: " + userId, exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void testGetUsersByRoles() {
        // Configura el mock
        RolUser role = RolUser.ADMIN;
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findByRoles(role)).thenReturn(mockUsers);

        // Ejecuta el método
        List<User> result = userService.getUsersByRoles(role);

        // Verifica el resultado
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findByRoles(role);
    }
}
