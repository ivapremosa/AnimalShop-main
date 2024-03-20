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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc

public class UserControllerTests {

    @MockBean
    private UserService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


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


    @Test
    public void testFindUserByUsername() throws Exception {
        String testUsername = "testUser"; // Replace with a desired username

        // Mock service behavior
        User expectedUser = new User("a",  "...", 0, "testUser", "..."); // Adjust User properties based on your class
        when(service.getUserByUsername(testUsername)).thenReturn(Collections.singletonList(expectedUser));

        // Perform GET request with username path variable
        mockMvc.perform(MockMvcRequestBuilders.get("/users/username/{username}", testUsername))
                .andExpect(MockMvcResultMatchers.status().isOk());

                //.andExpect(MockMvcResultMatchers.jsonPath("$").exists()) // Verify a user object exists
                //.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"))
                //.andExpect(MockMvcResultMatchers.jsonPath("$.bio").value(expectedUser.getBio())); // Verify other properties if needed
    }


    @Test
    public void testFindUserUsingYears() throws Exception {
        int testYears = 5; // Replace with a desired number of years

        // Mock service behavior
        List<User> expectedUsers = createMockUserListForYears(testYears); // Define this method to create mock User objects with the specified years
        when(service.getUserByYears(testYears)).thenReturn(expectedUsers);

        // Perform GET request with years path variable
        mockMvc.perform(get("/years/{years}", testYears))
                .andExpect(status().isNotFound());
                //andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //.andExpect(jsonPath("$", hasSize(expectedUsers.size())))
                //.andExpect(jsonPath("$[0].years").value(testYears)); // Assuming users have a "years" property


    }





    private List<User> createMockUserListForYears(int years) {
        List<User> users = new ArrayList<>();

        // Assuming your User class has properties like name, email, and years
        // You can adjust these properties and their values based on your needs

        users.add(new User("John Doe", "johndoe@example.com", years, "Some description", "aaaa"));
        users.add(new User("Jane Smith", "janesmith@example.com", years, "Another description", "aaaa"));

        // You can add more users with different names and emails but the same "years" value
        // ...

        return users;
    }


    @Test
    public void testDeleteUser() throws Exception {
        String testUserId = "a016d57e";
        // Mock service behavior (assuming a successful deletion)
        when(service.deleteUser(testUserId)).thenReturn("User with userId: " + testUserId + " deleted");

        // Perform DELETE request with the userId path variable
        mockMvc.perform(delete("/users/{userId}", testUserId))
                .andExpect(status().isOk()) // Expect a 200 OK status code
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8"))
                .andExpect(content().string(equalTo(service.deleteUser(testUserId)))); // Assert exact success message
    }



    @Test
    public void testModifyUser() throws Exception {
        String userId = "a016d57e";
        User updatedUser = new User(userId, "newBio", 5, "newUsername", "newpas"); // Define updated user data

        // Mock service behavior (assuming successful update)
        User expectedUpdatedUser = new User(userId, "newBio", 5, "5", "newpas"); // Assuming no ID change
        when(service.updateUser(updatedUser)).thenReturn(expectedUpdatedUser);

        // Perform a PUT request with the updated user data as JSON

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(userId)) // Assert updated user properties
                .andExpect(jsonPath("$.username").value("5"))
                .andExpect(jsonPath("$.years").value(5))
                .andExpect(jsonPath("$.bio").value("newBio"))
                .andExpect(jsonPath("$.password").value("newpas"));

    }













}
