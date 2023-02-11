package com.example.server;

import com.example.server.models.user.Role;
import com.example.server.models.user.User;
import com.example.server.repositories.UserRepository;
import com.example.server.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.example.server.controller.UserController;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {


    @TestConfiguration
    static class UserControllerTestConfiguration {
        @Autowired
        UserRepository userRepository;
        @Bean
        public UserService userService() {
            return new UserService(userRepository);
        }
    }

    private UserController userController;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    UserService userService;

    @Test
    @WithMockUser("ADMIN")
    void getAllUsersTest() throws Exception {
        User user1 = new User("user1", "password", Role.REGULAR_USER);
        User user2 = new User("user2", "password", Role.ADMIN);

        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users").with(csrf())).andExpect(status().is2xxSuccessful()).andExpect(content().string("[{\"username\":\"user1\",\"password\":\"password\",\"id\":null,\"role\":\"REGULAR_USER\"},{\"username\":\"user2\",\"password\":\"password\",\"id\":null,\"role\":\"ADMIN\"}]"));
    }

    @Test
    @WithMockUser("ADMIN")

    void addUserTest() throws Exception {
        User user = new User("user1", "password", Role.REGULAR_USER);
        Mockito.when(userService.addUser(user)).thenReturn(user);
        mockMvc.perform(post("/users").with(csrf())).andExpect(status().is2xxSuccessful()).andExpect(content().string(""));
    }

    @Test
    @WithMockUser("ADMIN")

    void removeUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", "1").with(csrf())).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser("ADMIN")

    void editUserTest() throws Exception {
        User modifiedUser = new User("lisa1", "pw", Role.REGULAR_USER);
        modifiedUser.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/" + modifiedUser.getId().toString()).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(modifiedUser))).andExpect(status().is2xxSuccessful());
    }






}
