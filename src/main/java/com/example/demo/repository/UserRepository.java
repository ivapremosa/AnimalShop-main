package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByYears(int years);
    @Query("{ username: ?0 }")
    List<User> getUsersByUsername(String username);



}
