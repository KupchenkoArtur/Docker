package ru.kata.spring.boot_security.demo.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private int id;
    @Column(name = "firstname")
    private String firstName;
    @Column
    private String surname;
    @Column
    private int age;
    @Column
    private String username;
    @Column
    private String password;
    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Role> roleList;


    public User(String firstName, String surname, int age, String username, String password, List<Role> roleList) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
        this.username = username;
        this.password = password;
        this.roleList = roleList;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
