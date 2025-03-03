package com.microrobot.gateway.htmlcontroller;

import com.microrobot.gateway.client.UserClient;
import com.microrobot.gateway.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v3/user")
public class UserViewController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/admin/all")
    public String getAllUsers(Model model) {
        List<UserDTO> users = userClient.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/admin/create")
    public String createUser(@ModelAttribute UserDTO userDTO, @RequestParam String roles) {
        userDTO.setRoles(Arrays.asList(roles.split(",")));
        userClient.createUser(userDTO);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userClient.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserDTO user = userClient.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO, @RequestParam String roles) {
        userDTO.setRoles(Arrays.asList(roles.split(",")));
        userClient.updateUser(id, userDTO);
        return "redirect:/users";
    }
}