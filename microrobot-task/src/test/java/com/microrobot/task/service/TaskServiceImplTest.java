package com.microrobot.task.service;

import com.microrobot.task.entities.Task;
import com.microrobot.task.entities.TaskStatus;
import com.microrobot.task.exception.entities.EntityNotFoundException;
import com.microrobot.task.persistence.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    void testGetAllTasks_ShouldReturnTasksList() {
        Task task1 = new Task(1L, "Task 1", "Description 1", 101L, TaskStatus.ASIGNADA);
        Task task2 = new Task(2L, "Task 2", "Description 2", 102L, TaskStatus.EN_PROCESO);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_WhenTaskExists_ShouldReturnTask() {
        Task task = new Task(1L, "Task 1", "Description 1", 101L, TaskStatus.ASIGNADA);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);

        assertNotNull(foundTask);
        assertEquals(1L, foundTask.getId());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_WhenTaskNotExists_ShouldThrowException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(1L));
        assertEquals("Task not found with id: 1", thrown.getMessage());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTask_ShouldReturnCreatedTask() {
        Task newTask = new Task(null, "New Task", "New Description", 103L, TaskStatus.ASIGNADA);
        Task savedTask = new Task(1L, "New Task", "New Description", 103L, TaskStatus.ASIGNADA);

        when(taskRepository.save(newTask)).thenReturn(savedTask);

        Task result = taskService.createTask(newTask);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    void testUpdateTask_WhenTaskExists_ShouldUpdateAndReturnTask() {
        Task existingTask = new Task(1L, "Old Title", "Old Description", 104L, TaskStatus.EN_PROCESO);
        Task updatedTaskData = new Task(1L, "Updated Title", "Updated Description", 104L, TaskStatus.FINALIZADA);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTaskData);

        Task result = taskService.updateTask(1L, updatedTaskData);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskStatus.FINALIZADA, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testDeleteTask_WhenTaskExists_ShouldDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTask_WhenTaskNotExists_ShouldThrowException() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> taskService.deleteTask(1L));
        assertEquals("Task not found with id: 1", thrown.getMessage());
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetTasksByUserId_ShouldReturnUserTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", 105L, TaskStatus.ASIGNADA);
        Task task2 = new Task(2L, "Task 2", "Description 2", 105L, TaskStatus.EN_PROCESO);

        when(taskRepository.findByAssignedUserId(105L)).thenReturn(Arrays.asList(task1, task2));

        List<Task> userTasks = taskService.getTasksByUserId(105L);

        assertEquals(2, userTasks.size());
        assertEquals(105L, userTasks.get(0).getAssignedUserId());
        verify(taskRepository, times(1)).findByAssignedUserId(105L);
    }
}
