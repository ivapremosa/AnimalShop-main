package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DemoApplicationTests {


//test


    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;




    @Test
    void shouldShowSimpleAssertion() {
        Assertions.assertEquals(1, 1);
    }


    @Test
    public void findAllUsersTest(){
        when(repository.findAll()).thenReturn(Stream
                .of(new User("123", "TEST", 32, "test", "test"), new User("123","ABC", 1, "DEF", "PO")).collect(Collectors.toList()));
        assertEquals(2, service.findAllUsers().size());
    }

    @Test
    public void saveUserTest() {
        User user = new User("999", "Pranya", 33, "Pune", "AA");
        when(repository.save(user)).thenReturn(user);
        assertEquals(user, service.addUser(user));
    }


    @Test
    public void getUserByUsernameTest() {
        String username = "a";
        when(repository.getUsersByUsername(username))
                .thenReturn(Stream.of(new User("376", "Danile", 31, "USA", "a")).collect(Collectors.toList()));
        assertEquals(1, service.getUserByUsername(username).size());
    }

    @Test
    public void getUserByUserId() {
        String userId = "a";
        when(repository.getUsersByUsername(userId))
                .thenReturn(Stream.of(new User("376", "Danile", 31, "USA", "a")).collect(Collectors.toList()));
        assertEquals(1, service.getUserByUsername(userId).size());
    }


    @Test
    public void getUsersByYearsTest() {
        // Mock user data

        int years = 12;
        List<User> expectedUserList = new ArrayList<>(); // Replace years with desired value

        // Mock repository behavior
        when(repository.findByYears(years)).thenReturn(expectedUserList);

        // Call the method under test
        List<User> actualUserList = service.getUserByYears(years);

        // Verify results
        assertThat(actualUserList).isNotNull();
        assertThat(actualUserList).isEqualTo(expectedUserList);

    }



    @Test
    public void deleteUserTest() throws Exception {

        String userId = "some-user-id";
        String result = service.deleteUser(userId);

        assertThat(result).isNotNull(); //a
    }


    @Test
    void shouldGetUserForGivenId() {
        User user = new User("aaa", "aaa0", 1, "IPhone12", "Electronics");


        assertThat(user.getPassword()).isEqualTo("Electronics");
        assertThat(user.getUsername()).isEqualTo("IPhone12");
        assertThat(user.getBio()).isEqualTo("aaa0");
        assertThat(user.getYears()).isEqualTo(1); //aaa
    }









}
