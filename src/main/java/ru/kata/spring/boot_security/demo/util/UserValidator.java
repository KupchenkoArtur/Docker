package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

@Component
public class UserValidator implements Validator {
    private  final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user= (User) o;

        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            errors.rejectValue("username","","Человек с таким именем пользователя существует");
        }



    }
}