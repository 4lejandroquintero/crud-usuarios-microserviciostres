package com.microrobot.user.controller;

import com.microrobot.user.entities.RolUser;
import com.microrobot.user.entities.User;
import com.microrobot.user.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/admin/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(iUserService.findAll());
    }

    @PostMapping("/admin/create")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return ResponseEntity.ok(iUserService.save(user));
    }

    @GetMapping("/all")
    public ResponseEntity<?> finAllUsers(){
        return ResponseEntity.ok(iUserService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(iUserService.findById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return ResponseEntity.ok(iUserService.updateUser(id, user));    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        iUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-by-role/{roles}")
    public ResponseEntity<List<User>> getUsersByRoles(@PathVariable RolUser roles) {
        return ResponseEntity.ok(iUserService.getUsersByRoles(roles));
    }


}
