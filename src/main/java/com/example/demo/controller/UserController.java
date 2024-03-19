package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user){
    return service.addUser(user);
    }

    @GetMapping
    public List<User> getUsers(){
        return service.findAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId){
        return service.getUserByUserId(userId);
    }

    @GetMapping("/years/{years}")
    public List<User> findUserUsingYears(@PathVariable int years){
        return service.getUserByYears(years);
    }

    @GetMapping("/username/{username}")
    public List<User> findUserByUsername(@PathVariable String username){
        return service.getUserByUsername(username);
    }

    @PutMapping
    public User modifyUser(@RequestBody User user){
        return service.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return  service.deleteUser(userId);
    }




}
