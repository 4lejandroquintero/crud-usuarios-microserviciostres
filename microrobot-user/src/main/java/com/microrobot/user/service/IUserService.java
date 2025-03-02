package com.microrobot.user.service;

import com.microrobot.user.entities.RolUser;
import com.microrobot.user.entities.User;
import com.microrobot.user.security.dto.AuthDTO;
import com.microrobot.user.security.dto.RegisterDTO;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    List<User> getUsersByRoles(RolUser roles);

    String register(RegisterDTO registerDTO);

    String login(AuthDTO authDTO);


}
