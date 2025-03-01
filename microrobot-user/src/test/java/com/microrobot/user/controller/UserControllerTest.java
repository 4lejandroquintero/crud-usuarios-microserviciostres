package com.microservice.user.controller;

import com.microservice.user.entities.RolUser;
import com.microservice.user.entities.User;
import com.microservice.user.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private IUserService iUserService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetAllUsers() {
        // Configura el mock
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(iUserService.findAll()).thenReturn(mockUsers);

        // Ejecuta el método
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(iUserService, times(1)).findAll();
    }

    @Test
    void testSaveUser() {
        // Configura el mock
        User mockUser = new User();
        mockUser.setFullName("John Doe");
        when(iUserService.save(mockUser)).thenReturn(mockUser);

        // Ejecuta el método
        ResponseEntity<User> response = userController.saveUser(mockUser);

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getFullName());
        verify(iUserService, times(1)).save(mockUser);
    }

    @Test
    void testFinAllUsers() {
        // Configura el mock
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(iUserService.findAll()).thenReturn(mockUsers);

        // Ejecuta el método
        ResponseEntity<?> response = userController.finAllUsers();

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, ((List<User>) response.getBody()).size());
        verify(iUserService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Configura el mock
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFullName("John Doe");
        when(iUserService.findById(userId)).thenReturn(mockUser);

        // Ejecuta el método
        ResponseEntity<?> response = userController.findById(userId);

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, ((User) response.getBody()).getId());
        assertEquals("John Doe", ((User) response.getBody()).getFullName());
        verify(iUserService, times(1)).findById(userId);
    }

    @Test
    void testUpdateUser() {
        // Configura el mock
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFullName("John Doe");
        when(iUserService.updateUser(userId, mockUser)).thenReturn(mockUser);

        // Ejecuta el método
        ResponseEntity<User> response = userController.updateUser(userId, mockUser);

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody().getId());
        assertEquals("John Doe", response.getBody().getFullName());
        verify(iUserService, times(1)).updateUser(userId, mockUser);
    }

    @Test
    void testDeleteUser() {
        // Configura el mock
        Long userId = 1L;
        doNothing().when(iUserService).deleteUser(userId);

        // Ejecuta el método
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Verifica el resultado
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(iUserService, times(1)).deleteUser(userId);
    }

    @Test
    void testGetUsersByRoles() {
        // Configura el mock
        RolUser role = RolUser.ADMIN;
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(iUserService.getUsersByRoles(role)).thenReturn(mockUsers);

        // Ejecuta el método
        ResponseEntity<List<User>> response = userController.getUsersByRoles(role);

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(iUserService, times(1)).getUsersByRoles(role);
    }
}
