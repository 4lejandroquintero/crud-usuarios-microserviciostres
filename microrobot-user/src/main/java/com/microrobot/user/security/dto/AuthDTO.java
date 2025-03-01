package com.microrobot.user.security.dto;

import lombok.Data;

@Data
public class AuthDTO {
    private String email;
    private String password;
}
