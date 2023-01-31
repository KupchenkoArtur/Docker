package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.role.RoleService;
import ru.kata.spring.boot_security.demo.service.user.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }


    @GetMapping
    public String printWelcome(ModelMap model, Principal principal) {
        Optional<User> user =userService.findByUserName(principal.getName());
        List<User> users = userService.showAll();

        model.addAttribute("users", users);
        model.addAttribute("principal",user.get());
        model.addAttribute("newUser",new User());
        model.addAttribute("roles", roleService.showAll());

        return "admin/index";
    }

    @PostMapping
    public String create(@ModelAttribute("newUser") User user,@RequestParam("roles") int[] rolesId) {
        List<Role> roles=new ArrayList<>();

        for (int role:rolesId) {
            roles.add(roleService.show(role));
        }
        user.setRoleList(roles);

        userService.create(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userService.show(id));

        return "admin/show";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("user") User user,@RequestParam("roles") int[] rolesId) {
        List<Role> roles=new ArrayList<>();

        for (int role:rolesId) {
            roles.add(roleService.show(role));
        }

        user.setPassword(userService.show(id).getPassword());
        user.setRoleList(roles);

        userService.update(id, user);

        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user, Model model) {

        model.addAttribute("roles", roleService.showAll());

        return "admin/newUser";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {

        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", roleService.showAll());

        return "admin/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        userService.delete(id);

        return "redirect:/admin";
    }

}
