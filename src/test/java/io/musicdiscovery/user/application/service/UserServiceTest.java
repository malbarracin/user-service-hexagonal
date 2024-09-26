package io.musicdiscovery.user.application.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.musicdiscovery.user.application.port.output.UserPersistencePort;
import io.musicdiscovery.user.domain.exception.UserNotFoundException;
import io.musicdiscovery.user.domain.model.User;
import reactor.core.publisher.Mono;

public class UserServiceTest {

    @Mock
    private UserPersistencePort userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User("1", "John Doe", "john@example.com", List.of(), List.of());
    }

    /**
     * Tests the getUserById method for a valid user ID.
     */
    @Test
    public void testGetUserById_UserExists() {
        when(userRepository.findById("1")).thenReturn(Mono.just(testUser));

        Mono<User> result = userService.getUserById("1");

        assertNotNull(result);
        assertEquals("John Doe", result.block().getName());
        verify(userRepository).findById("1");
    }

    /**
     * Tests the getUserById method for a non-existing user ID.
     */
    @Test
    public void testGetUserById_UserDoesNotExist() {
        when(userRepository.findById("999")).thenReturn(Mono.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById("999").block();
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
        verify(userRepository).findById("999");
    }

    /**
     * Tests the createUser method for successfully creating a user.
     */
    @Test
    public void testCreateUser() {
        when(userRepository.save(testUser)).thenReturn(Mono.just(testUser));

        Mono<User> result = userService.createUser(testUser);

        assertNotNull(result);
        assertEquals("John Doe", result.block().getName());
        verify(userRepository).save(testUser);
    }

    /**
     * Tests the updateUser method for updating an existing user.
     */
    @Test
    public void testUpdateUser_UserExists() {
        when(userRepository.findById("1")).thenReturn(Mono.just(testUser));
        User updatedUser = new User("1", "Jane Doe", "jane@example.com", List.of(), List.of());

        when(userRepository.save(testUser)).thenReturn(Mono.just(updatedUser));

        Mono<User> result = userService.updateUser("1", updatedUser);

        assertNotNull(result);
        assertEquals("Jane Doe", result.block().getName());
        verify(userRepository).findById("1");
        verify(userRepository).save(testUser);
    }

    /**
     * Tests the updateUser method for a non-existing user ID.
     */
    @Test
    public void testUpdateUser_UserDoesNotExist() {
        when(userRepository.findById("999")).thenReturn(Mono.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser("999", testUser).block();
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
        verify(userRepository).findById("999");
    }

    /**
     * Tests the getAllUsers method to ensure it returns a list of users.
     */
    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Mono.just(List.of(testUser)));

        Mono<List<User>> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.block().size());
        assertEquals("John Doe", result.block().get(0).getName());
        verify(userRepository).findAll();
    }

    /**
     * Tests the deleteUser method for a valid user ID.
     */
    @Test
    public void testDeleteUser_UserExists() {
        when(userRepository.findById("1")).thenReturn(Mono.just(testUser));
        when(userRepository.deleteById("1")).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteUser("1");

        assertNotNull(result);
        assertDoesNotThrow(() -> result.block());
        verify(userRepository).findById("1");
        verify(userRepository).deleteById("1");
    }


}
