package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @GetMapping("/menu")
    public String menu() {
        return "dashboard/menu.template";
    }
    @GetMapping("/roles")
    public String roles() {
        return "dashboard/roles.template";
    }
    @GetMapping("/users")
    public String users() {
        return "dashboard/users.template";
    }
    @GetMapping({"/", "/home"})
    public String home() {
        return "dashboard/home";
    }
    @RequestMapping("/sesion")
    public String error() {
        return "dashboard/session.template";
    }

}
