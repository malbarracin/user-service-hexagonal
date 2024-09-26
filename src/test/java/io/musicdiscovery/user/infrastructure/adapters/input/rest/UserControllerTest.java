package io.musicdiscovery.user.infrastructure.adapters.input.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.musicdiscovery.user.application.port.input.UserServicePort;
import io.musicdiscovery.user.domain.exception.UserNotFoundException;
import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.domain.model.enums.Genre;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request.UserCreateRequest;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.response.UserResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserControllerTest {

    @Mock
    private UserServicePort userServicePort;

    @Mock
    private UserRestMapper restMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to retrieve a user by ID when the user exists.
     */
    @Test
    void testGetUserById_UserExists() {
        User mockUser = new User("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));
        UserResponse mockResponse = new UserResponse("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));

        when(userServicePort.getUserById(anyString())).thenReturn(Mono.just(mockUser));
        when(restMapper.toUserResponse(mockUser)).thenReturn(mockResponse);

        Mono<UserResponse> result = userController.getUserById("123");

        StepVerifier.create(result)
                .expectNext(mockResponse)
                .verifyComplete();

        verify(userServicePort).getUserById("123");
        verify(restMapper).toUserResponse(mockUser);
    }

    /**
     * Test to retrieve a user by ID when the user does not exist.
     */
    @Test
    void testGetUserById_UserDoesNotExist() {
        when(userServicePort.getUserById(anyString())).thenReturn(Mono.empty());

        Mono<UserResponse> result = userController.getUserById("123");

        StepVerifier.create(result)
                .expectError(UserNotFoundException.class)
                .verify();

        verify(userServicePort).getUserById("123");
        verify(restMapper, never()).toUserResponse(any());
    }

    /**
     * Test to create a new user.
     */
    @Test
    void testCreateUser() {
        UserCreateRequest createRequest = new UserCreateRequest("Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));
        User mockUser = new User(null, "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));
        User savedUser = new User("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));
        UserResponse mockResponse = new UserResponse("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));

        when(userServicePort.createUser(any(User.class))).thenReturn(Mono.just(savedUser));
        when(restMapper.toUser(any(UserCreateRequest.class))).thenReturn(mockUser);
        when(restMapper.toUserResponse(any(User.class))).thenReturn(mockResponse);

        Mono<UserResponse> result = userController.createUser(createRequest);

        StepVerifier.create(result)
                .expectNext(mockResponse)
                .verifyComplete();

        verify(userServicePort).createUser(mockUser);
        verify(restMapper).toUser(createRequest);
        verify(restMapper).toUserResponse(savedUser);
    }

    /**
     * Test to update a user when the user exists.
     */
    @Test
    void testUpdateUser_UserExists() {
    	 User mockUser = new User("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));
         UserResponse mockResponse = new UserResponse("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));

         when(userServicePort.getUserById("123")).thenReturn(Mono.just(mockUser));
         when(restMapper.toUserResponse(mockUser)).thenReturn(mockResponse);

         Mono<UserResponse> result = userController.getUserById("123");

         StepVerifier.create(result)
                 .expectNext(mockResponse)
                 .verifyComplete();

         verify(userServicePort).getUserById("123");
         verify(restMapper).toUserResponse(mockUser);
    }

    /**
     * Test to update a user when the user does not exist.
     */
    @Test
    void testUpdateUser_UserDoesNotExist() {
    	when(userServicePort.getUserById("123")).thenReturn(Mono.error(new UserNotFoundException("User not found with ID: 123")));

        Mono<UserResponse> result = userController.getUserById("123");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UserNotFoundException && throwable.getMessage().equals("User not found with ID: 123"))
                .verify();

        verify(userServicePort).getUserById("123");
    }

    /**
     * Test to get all users.
     */
    @Test
    void testGetAllUsers_Success() {
    	
    	UserCreateRequest request = new UserCreateRequest("Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));
        User mockUser = new User("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));
        UserResponse mockResponse = new UserResponse("123", "Marcelo", "marcelo@gmail.com", List.of(Genre.ROCK), List.of("The Beatles"));

        when(restMapper.toUser(any(UserCreateRequest.class))).thenReturn(mockUser);
        when(userServicePort.createUser(mockUser)).thenReturn(Mono.just(mockUser));
        when(restMapper.toUserResponse(mockUser)).thenReturn(mockResponse);

        Mono<UserResponse> result = userController.createUser(request);

        StepVerifier.create(result)
                .expectNext(mockResponse)
                .verifyComplete();

        verify(restMapper).toUser(request);
        verify(userServicePort).createUser(mockUser);
        verify(restMapper).toUserResponse(mockUser);
    }

    /**
     * Test to delete a user when the user exists.
     */
    @Test
    void testDeleteUser_UserExists() {
        User user = new User("123", "Test User", "test@example.com", List.of(), List.of());

        when(userServicePort.getUserById("123")).thenReturn(Mono.just(user));
        when(userServicePort.deleteUser("123")).thenReturn(Mono.empty());

        Mono<Void> result = userController.deleteUser("123");

        StepVerifier.create(result)
                .verifyComplete();

        verify(userServicePort).deleteUser("123");
    }

    /**
     * Test to delete a user when the user does not exist.
     */
    @Test
    void testDeleteUser_UserDoesNotExist() {

        when(userServicePort.getUserById("123")).thenReturn(Mono.error(new UserNotFoundException("User not found with ID: 123")));

        Mono<Void> result = userController.deleteUser("123");

        StepVerifier.create(result)
            .expectErrorMatches(throwable -> throwable instanceof UserNotFoundException &&
                throwable.getMessage().equals("User not found with ID: 123"))
            .verify();

        verify(userServicePort).getUserById("123");
        verify(userServicePort, never()).deleteUser("123");
    }
}
