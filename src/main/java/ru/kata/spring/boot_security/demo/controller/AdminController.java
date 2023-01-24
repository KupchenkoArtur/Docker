package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder, UserService userService, UserValidator userValidator,
                           RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleRepository = roleRepository;
    }


    @GetMapping
    public String printWelcome(ModelMap model) {

        List<User> users = userService.showAll();
        model.addAttribute("users", users);

        return "admin/index";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "admin/newUser";

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.create(user);

        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userService.show(id));

        return "admin/show";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {

        user.setPassword(userService.show(id).getPassword());

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "/admin/edit";
        }

        userService.update(id, user);

        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user, Model model) {

        model.addAttribute("roles", roleRepository.findAll());

        return "admin/newUser";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {

        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", roleRepository.findAll());

        return "admin/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {

        userService.delete(id);

        return "redirect:/admin";
    }

}
