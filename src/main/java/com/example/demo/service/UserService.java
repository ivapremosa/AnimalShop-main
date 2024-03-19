package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    //CRUD

    public User addUser(User user){
        user.setUserId(UUID.randomUUID().toString().split("-")[0]);
        System.out.println("New user added successfully");
        //System.out.println(repository.findById(user.getUserId()).get());
        return repository.save(user);
    }

    public List<User> findAllUsers(){
        List<User> users = repository.findAll();
        System.out.println("Getting all users from DB : " + users);
        return users;
    }

    public User getUserByUserId(String userId){
        System.out.println("User by userId: " + userId + " found");
        System.out.println(repository.findById(userId).get());
        return repository.findById(userId).get();
    }

    public List<User> getUserByYears(int years){
        System.out.println("User by years: " + years + " found");
        return repository.findByYears(years);
    }

    public List<User> getUserByUsername(String username){
        System.out.println("User by username: " + username + " found");
        System.out.println(repository.getUsersByUsername(username));
        return repository.getUsersByUsername(username);
    }

    public User updateUser(User userRequest){
        //get the existing document from DB
        //populate new value from request to existing object

        User existingUser = repository.findById(userRequest.getUserId()).get();
        existingUser.setBio(userRequest.getBio());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setUsername(userRequest.getUsername());
        existingUser.setYears(userRequest.getYears());
        System.out.println("User: " + existingUser + " updated");
        System.out.println(repository.findById(userRequest.getUserId()).get());
        return repository.save(existingUser);
    }

    public String deleteUser(String userId){
        repository.deleteById(userId);
        System.out.println("User with userId " + userId + " deleted");
        return "User with userId: " +userId+" deleted";
    }




}
