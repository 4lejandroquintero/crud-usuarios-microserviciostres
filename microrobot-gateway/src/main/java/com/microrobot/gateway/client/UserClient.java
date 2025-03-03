package com.microrobot.gateway.client;

import com.microrobot.gateway.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "users", url = "http://localhost:8090/api/v3/user", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/admin/all")
    List<UserDTO> getAllUsers();

    @GetMapping("/search/{id}")
    UserDTO getUserById(@PathVariable("id") Long id);

    @PostMapping("/admin/create")
    UserDTO createUser(@RequestBody UserDTO userDTO);

    @PutMapping("/update/{id}")
    UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO);

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id);
}
