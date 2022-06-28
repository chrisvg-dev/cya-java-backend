package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @GetMapping("/menu")
    public String menu() {
        return "dashboard/menu";
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
