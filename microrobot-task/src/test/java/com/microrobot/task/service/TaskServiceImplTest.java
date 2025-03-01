package com.microservice.task.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.microservice.task.entities.Task;
import com.microservice.task.entities.TaskStatus;
import com.microservice.task.exception.EntityNotFoundException;
import com.microservice.task.persistence.ITaskRepository;
import com.microservice.task.service.TaskServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class TaskServiceImplTest {

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.EN_PROCESO);
    }

    @Test
    void getAllTasks_ShouldReturnList() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        assertEquals(1, taskService.getAllTasks().size());
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertEquals(task, taskService.getTaskById(1L));
    }

    @Test
    void getTaskById_ShouldThrowException_WhenNotExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void createTask_ShouldReturnSavedTask() {
        when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskService.createTask(task));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("Updated Description");
        updatedTask.setStatus(TaskStatus.FINALIZADA);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void deleteTask_ShouldDelete_WhenExists() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenNotExists() {
        when(taskRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> taskService.deleteTask(1L));
    }
}
