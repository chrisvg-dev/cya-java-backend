package com.cvg.cya.postulacion.controller;

import com.cvg.cya.postulacion.models.repository.DisplayRepository;
import com.cvg.cya.postulacion.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Lazy;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {
    private static final Logger LOG = LoggerFactory.getLogger( PageController.class );
    private final UserService userService;
    private final DisplayRepository displayRepository;

    public PageController(@Lazy UserService userService, @Lazy DisplayRepository displayRepository) {
        this.userService = userService;
        this.displayRepository = displayRepository;
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
    @GetMapping({"/", "/home", "cya"})
    public String cya(HttpSession session) {
        String message = this.displayRepository.findById(1L).orElseThrow().getMessage();
        session.setAttribute("message", message);
        return "dashboard/ca.template";
    }

    @RequestMapping("/sesion")
    public String session(HttpSession session) {
        session.setAttribute("duration", session.getMaxInactiveInterval());
        return "dashboard/session.template";
    }

}
