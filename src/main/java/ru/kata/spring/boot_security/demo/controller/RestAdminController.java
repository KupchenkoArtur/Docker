package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class RestAdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.showAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/principal")
    public  User getPrincipal(Principal principal) {

        Optional<User> user = userService.findByUserName(principal.getName());

        return user.get();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.show(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.create(user);

        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.update(user);
        return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser( @PathVariable("id") int id) {
        User user = userService.show(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/roles")
    public  ResponseEntity<List<Role>> gerAllRoles(){
        List<Role> roles = roleService.showAll();

        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
