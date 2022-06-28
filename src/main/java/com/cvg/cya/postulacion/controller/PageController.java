package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.dto.UserDto;
import com.cvg.cya.postulacion.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class PageController {

    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
    @GetMapping({"", "/home"})
    public String home() {
        return "dashboard/home";
    }

}
