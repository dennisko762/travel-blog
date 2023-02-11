package com.example.server;

import com.example.server.models.user.Role;
import com.example.server.models.user.User;
import com.example.server.repositories.UserRepository;
import com.example.server.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
           user = new User("Tony", "password", Role.REGULAR_USER);
           user.setId(1L);
       }
        @Test
        void getAllUsersTest() {
            User user1 = new User("testuser", "password", Role.REGULAR_USER);
            user1.setId(2L);
            Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user, user1));
            List<User> userList = userService.getAllUsers();
            assertThat(userList).isNotNull();
            assertThat(userList.get(0).getUsername()).isEqualTo("Tony");
        }

        @Test
    void addUserTest() {
        User user2 = new User("Vitali", "password", Role.ADMIN);
        user2.setId(2L);
        Mockito.when(userRepository.findById(user2.getId())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user2)).thenReturn(user2);
        User savedUser = userService.addUser(user2);
        assertThat(savedUser.getUsername()).isEqualTo("Vitali");
        }

    @Test
    void editUserTest() {
        User existingUser1 = new User("Vitali", "password", Role.ADMIN);
        existingUser1.setId(1L);
        User existingUser2 = new User("Pavel", "password", Role.ADMIN);
        existingUser2.setId(1L);
        Mockito.when(userRepository.save(existingUser1)).thenReturn(existingUser1);
        Mockito.when(userRepository.save(existingUser2)).thenReturn(existingUser2);
        existingUser1.setUsername("Vitaliii");
        User modifiedUser = userService.editUser(existingUser1.getId(), existingUser1);
        assertThat(modifiedUser.getUsername()).isEqualTo("Vitaliii");
    }

    @Test
    void deleteUserTest() {
        User user = new User("Vitali", "password", Role.ADMIN);
        user.setId(1L);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userService.deleteUserById(user.getId());
        List<User> userList = userService.getAllUsers();
        assertThat(userList.isEmpty()).isEqualTo(true);
    }


}
