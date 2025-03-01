package com.microrobot.gateway.htmlcontroller;

import com.microrobot.gateway.client.TaskClient;
import com.microrobot.gateway.dto.TaskDTO;
import com.microrobot.gateway.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/v3/tasks")
public class TaskViewController {

    @Autowired
    private TaskClient taskClient;

    @GetMapping("/admin/all")
    public String getAllTasks(Model model) {
        List<TaskDTO> tasks = taskClient.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute TaskDTO taskDTO, @RequestParam String status) {
        taskDTO.setStatus(Arrays.asList(status.split(",")));
        taskClient.createTask(taskDTO);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskClient.deleteTask(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        TaskDTO task = taskClient.getTaskById(id);
        model.addAttribute("tasks", task);
        return "edit-task";
    }

    @PutMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute TaskDTO taskDTO, @RequestParam String status) {
        try {
            taskDTO.setStatus(Collections.singletonList(status));
            taskClient.updateTask(id, taskDTO);
        } catch (IllegalArgumentException e) {
            return "redirect:/tasks?error=invalid_status";
        }
        return "redirect:/tasks";
    }
}