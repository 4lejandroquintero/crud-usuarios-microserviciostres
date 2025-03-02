package com.microrobot.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microrobot.user.entities.RolUser;
import com.microrobot.user.entities.User;
import com.microrobot.user.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IUserService iUserService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(iUserService.findAll()).thenReturn(mockUsers);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(iUserService, times(1)).findAll();
    }

    @Test
    void testSaveUser() {
        User mockUser = new User();
        mockUser.setFullName("Alejandro Quintero");
        when(iUserService.save(mockUser)).thenReturn(mockUser);

        ResponseEntity<User> response = userController.saveUser(mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alejandro Quintero", response.getBody().getFullName());
        verify(iUserService, times(1)).save(mockUser);
    }

    @Test
    void testFinAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(iUserService.findAll()).thenReturn(mockUsers);

        ResponseEntity<?> response = userController.finAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, ((List<User>) response.getBody()).size());
        verify(iUserService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFullName("Alejandro Quintero");
        when(iUserService.findById(userId)).thenReturn(mockUser);

        ResponseEntity<?> response = userController.findById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, ((User) response.getBody()).getId());
        assertEquals("Alejandro Quintero", ((User) response.getBody()).getFullName());
        verify(iUserService, times(1)).findById(userId);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFullName("Alejandro Quintero");
        when(iUserService.updateUser(userId, mockUser)).thenReturn(mockUser);

        ResponseEntity<User> response = userController.updateUser(userId, mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody().getId());
        assertEquals("Alejandro Quintero", response.getBody().getFullName());
        verify(iUserService, times(1)).updateUser(userId, mockUser);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        doNothing().when(iUserService).deleteUser(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(iUserService, times(1)).deleteUser(userId);
    }

    @Test
    void testGetUsersByRoles() {
        RolUser role = RolUser.ADMIN;
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(iUserService.getUsersByRoles(role)).thenReturn(mockUsers);

        ResponseEntity<List<User>> response = userController.getUsersByRoles(role);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(iUserService, times(1)).getUsersByRoles(role);
    }
}
