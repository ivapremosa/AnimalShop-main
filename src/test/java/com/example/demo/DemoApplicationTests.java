package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }
//test
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;

    /*@Test
    void shouldShowSimpleAssertion() {
        Assertions.assertEquals(1, 1);
    } */

    @Test
    public void findAllUsersTest(){
        when(repository.findAll()).thenReturn(Stream
                .of(new User("123", "TEST", 32, "test", "test"), new User("123","ABC", 1, "DEF", "PO")).collect(Collectors.toList()));
        assertEquals(2, service.findAllUsers().size());
    }



}
