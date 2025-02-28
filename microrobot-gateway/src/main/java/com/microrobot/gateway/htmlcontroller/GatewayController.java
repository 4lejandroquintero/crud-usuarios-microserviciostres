package com.microrobot.gateway.htmlcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class GatewayController {

    @GetMapping("/welcome")
    public String welcome(@RequestParam(name = "username", defaultValue = "Guest") String username, Model model) {
        model.addAttribute("username", username);
        return "welcome"; // Nombre de la plantilla (sin extensión)
    }
}