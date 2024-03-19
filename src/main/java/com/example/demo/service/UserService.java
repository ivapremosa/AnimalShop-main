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
        return repository.save(user);
    }

    public List<User> findAllUsers(){
        return repository.findAll();
    }

    public User getUserByUserId(String userId){
        return repository.findById(userId).get();
    }

    public List<User> getUserByYears(int years){
        return repository.findByYears(years);
    }

    public List<User> getUserByUsername(String username){
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

        return repository.save(existingUser);
    }

    public String deleteUser(String userId){
        repository.deleteById(userId);
        return userId+" deleted";
    }


}
