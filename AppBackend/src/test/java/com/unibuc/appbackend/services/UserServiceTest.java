package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.exceptions.UserNotFoundException;
import com.unibuc.appbackend.interfaces.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void create() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setUserId(UUID.randomUUID());
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword("encodedPassword");
        when(userRepository.save(user)).thenReturn(savedUser);

        doNothing().when(userRoleService).addRoleForUser(user);

        User result = userService.create(user);

        assertNotNull(result);
        assertEquals(savedUser.getUserId(), result.getUserId());
        assertEquals(savedUser.getFirstName(), result.getFirstName());
        assertEquals(savedUser.getLastName(), result.getLastName());
        assertEquals(savedUser.getEmail(), result.getEmail());
        assertEquals(savedUser.getPassword(), result.getPassword());

        verify(userRepository).save(user);
        verify(bCryptPasswordEncoder).encode(any(CharSequence.class));
        verify(userRoleService).addRoleForUser(user);
    }

    @Test
    void loginValidUserCredentials_shouldReturnUser() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        User userFromDB = new User();
        userFromDB.setEmail("john.doe@example.com");
        userFromDB.setPassword(bCryptPasswordEncoder.encode("password123"));

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(userFromDB));
        when(bCryptPasswordEncoder.matches(user.getPassword(), userFromDB.getPassword())).thenReturn(true);

        User result = userService.login(user);

        assertNotNull(result);
        assertEquals(userFromDB.getEmail(), result.getEmail());
        assertEquals(userFromDB.getPassword(), result.getPassword());

        verify(userRepository).findByEmail(user.getEmail());
        verify(bCryptPasswordEncoder).matches(user.getPassword(), userFromDB.getPassword());
    }

    @Test
    void loginInvalidUserCredentials_shouldReturnNull() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("wrongPassword");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        User result = userService.login(user);

        assertNull(result);

        verify(userRepository).findByEmail(user.getEmail());
        verifyNoInteractions(bCryptPasswordEncoder);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<User> userList = Arrays.asList(
                new User(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "password123"),
                new User(UUID.randomUUID(), "Jane", "Doe", "jane.doe@example.com", "password456")
        );

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(userList.size(), result.size());
        assertEquals(userList.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(userList.get(1).getEmail(), result.get(1).getEmail());

        verify(userRepository).findAll();
    }

    @Test
    void changePassword_validUser_shouldChangePassword() {

        UUID userId = UUID.randomUUID();
        String newPassword = "newPassword";

        User user = new User(userId, "John", "Doe", "john.doe@example.com", "oldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        userService.changePassword(userId, newPassword);

        verify(userRepository).findById(userId);
        verify(bCryptPasswordEncoder).encode(newPassword);
        verify(userRepository).save(user);
    }

    @Test
    void changePassword_userNotFound_shouldThrowUserNotFoundException() {
        UUID userId = UUID.randomUUID();
        String newPassword = "newPassword";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.changePassword(userId, newPassword));

        verify(userRepository).findById(userId);
    }

    @Test
    void delete_validUser_shouldDeleteUser() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User(userId, "John", "Doe", "john.doe@example.com", "password123")));

        userService.delete(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void delete_userNotFound_shouldThrowUserNotFoundException() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(userId));

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void getUserById_validUser() {
        UUID userId = UUID.randomUUID();
        User sampleUser = new User(userId, "John", "Doe", "john.doe@example.com", "password123");

        when(userRepository.findById(userId)).thenReturn(Optional.of(sampleUser));

        User resultUser = userService.getUserById(userId);

        assertEquals(sampleUser, resultUser);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void getUserById_userNotFound_shouldThrowUserNotFoundException() {
        UUID nonExistingUserId = UUID.randomUUID();

        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(nonExistingUserId));

        verify(userRepository, times(1)).findById(nonExistingUserId);
    }
}
