package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.user.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class RestUserController {
    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/principal")
    public User getPrincipal(Principal principal) {

        Optional<User> user = userService.findByUserName(principal.getName());

        return user.get();
    }
}
