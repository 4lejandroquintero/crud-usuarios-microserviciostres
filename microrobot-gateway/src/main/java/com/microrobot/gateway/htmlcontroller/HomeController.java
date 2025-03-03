package com.microrobot.gateway.htmlcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/auth/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/auth/register")
    public String showRegisterPage() {
        return "register";
    }
}
