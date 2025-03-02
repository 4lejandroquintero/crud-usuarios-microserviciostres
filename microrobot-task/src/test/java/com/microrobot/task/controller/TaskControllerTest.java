package com.microrobot.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microrobot.task.entities.Task;
import com.microrobot.task.service.ITaskService;
import com.microrobot.task.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.microrobot.task.entities.TaskStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ITaskService iTaskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private TaskServiceImpl taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setup() {
        assertNotNull(mockMvc, "MockMvc no debe ser nulo");
        assertNotNull(objectMapper, "ObjectMapper no debe ser nulo");
    }

    @Test
    void testGetAllTasks() {
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        when(taskService.getAllTasks()).thenReturn(mockTasks);

        ResponseEntity<List<Task>> response = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testGetTaskById() {
        Long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setTitle("Task 1");
        when(taskService.getTaskById(taskId)).thenReturn(mockTask);

        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskId, response.getBody().getId());
        assertEquals("Task 1", response.getBody().getTitle());
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    void testCreateTask() {
        Task mockTask = new Task();
        mockTask.setTitle("New Task");
        when(taskService.createTask(mockTask)).thenReturn(mockTask);

        ResponseEntity<Task> response = taskController.createTask(mockTask);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Task", response.getBody().getTitle());
        verify(taskService, times(1)).createTask(mockTask);
    }

    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        Task mockTask = new Task();
        mockTask.setId(taskId);
        mockTask.setTitle("Updated Task");
        when(taskService.updateTask(taskId, mockTask)).thenReturn(mockTask);

        ResponseEntity<Task> response = taskController.updateTask(taskId, mockTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskId, response.getBody().getId());
        assertEquals("Updated Task", response.getBody().getTitle());
        verify(taskService, times(1)).updateTask(taskId, mockTask);
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    void testGetTasksByUserId() {
        Long userId = 1L;
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());
        when(taskService.getTasksByUserId(userId)).thenReturn(mockTasks);

        ResponseEntity<List<Task>> response = taskController.getTasksByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(taskService, times(1)).getTasksByUserId(userId);
    }
}
