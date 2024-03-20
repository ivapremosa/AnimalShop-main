package com.example.demo;



import com.example.demo.controller.UserController;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.demo.model.User;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.ErrorResponse;
import org.springframework.web.context.WebApplicationContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(UserController.class)
@AutoConfigureMockMvc

public class UserControllerTests {

    @MockBean
    private UserService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;


    @Test
    public void findAllUsersC() throws Exception {
        when(service.findAllUsers()).thenReturn(List.of(new User("duke", "duke@spring.io", 1, "duke", "bb")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("duke"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bio").value("duke@spring.io"));
    }

    @Test
    public void saveUserTestC() throws Exception {

        //mock the user data that we have to save
        User user = new User();

        user.setBio("John loves animals");
        user.setUsername("com");
        user.setYears(65);
        user.setPassword("malo");

        when(service.addUser(any(User.class))).thenReturn(user);

        //mock request "/user"

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bio").value("John loves animals"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.years").value("65"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("malo"));

    }










}
