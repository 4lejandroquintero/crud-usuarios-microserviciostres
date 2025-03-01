package com.microservice.task.controller;

import com.microservice.task.entities.Task;
import com.microservice.task.service.TaskServiceImpl;
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
public class TaskControllerTest {

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void testGetAllTasks() {
        // Configura el mock
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        when(taskService.getAllTasks()).thenReturn(mockTasks);

        // Ejecuta el método
        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testGetTaskById() {
        // Configura el mock
        Long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setTitle("Task 1");
        when(taskService.getTaskById(taskId)).thenReturn(mockTask);

        // Ejecuta el método
        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskId, response.getBody().getId());
        assertEquals("Task 1", response.getBody().getTitle());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    void testCreateTask() {
        // Configura el mock
        Task mockTask = new Task();
        mockTask.setTitle("New Task");
        when(taskService.createTask(mockTask)).thenReturn(mockTask);

        // Ejecuta el método
        ResponseEntity<Task> response = taskController.createTask(mockTask);

        // Verifica el resultado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Task", response.getBody().getTitle());
        verify(taskService, times(1)).createTask(mockTask);
    }

    @Test
    void testUpdateTask() {
        // Configura el mock
        Long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setTitle("Updated Task");
        when(taskService.updateTask(taskId, mockTask)).thenReturn(mockTask);

        // Ejecuta el método
        ResponseEntity<Task> response = taskController.updateTask(taskId, mockTask);

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskId, response.getBody().getId());
        assertEquals("Updated Task", response.getBody().getTitle());
        verify(taskService, times(1)).updateTask(taskId, mockTask);
    }

    @Test
    void testDeleteTask() {
        // Configura el mock
        Long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        // Ejecuta el método
        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        // Verifica el resultado
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    void testGetTasksByUserId() {
        // Configura el mock
        Long userId = 1L;
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByUserId(userId)).thenReturn(mockTasks);

        // Ejecuta el método
        ResponseEntity<List<Task>> response = taskController.getTasksByUserId(userId);

        // Verifica el resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getTasksByUserId(userId);
    }


}
