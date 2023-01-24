package ru.kata.spring.boot_security.demo.service;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> showAll() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Hibernate.initialize(user);
        }
        return users;
    }

    @Override
    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public User show(int id) {
        User user = userRepository.findById(id).get();
        return user;
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
