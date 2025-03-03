package com.microrobot.gateway.client;

import com.microrobot.gateway.dto.TaskDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "tasks", url = "http://localhost:9090/api/v3/tasks")
public interface TaskClient {

    @GetMapping
    List<TaskDTO> getAllTasks();

    @GetMapping("/{id}")
    TaskDTO getTaskById(@PathVariable("id") Long id);

    @PostMapping
    TaskDTO createTask(@RequestBody TaskDTO taskDTO);

    @PutMapping("/{id}")
    TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO);

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Long id);

    @GetMapping("/user/{userId}")
    List<TaskDTO> getTasksByUserId(@PathVariable Long userId);

}










