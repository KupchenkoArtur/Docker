package ru.kata.spring.boot_security.demo.service;







import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> showAll();
    public void create(User  user);
    public User show(int id);
    public void delete(int id);
    public void update(int id,User user);
    public Optional<User> findByUserName(String username);
}
