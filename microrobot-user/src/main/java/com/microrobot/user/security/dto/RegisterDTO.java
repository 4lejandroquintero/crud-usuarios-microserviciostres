package com.microrobot.user.security.dto;

import com.microrobot.user.entities.RolUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterDTO {
    private String fullName;
    private String email;
    private String password;
    private Set<RolUser> roles;
}
