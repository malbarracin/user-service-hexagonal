package io.musicdiscovery.user.infrastructure.adapters.input.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import io.musicdiscovery.user.domain.model.enums.Mood;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request.UpdateMoodRequest;
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
        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setName("Marcelo");
        mockUser.setEmail("marcelo@gmail.com");
        mockUser.setFavoriteArtist(List.of("The Beatles"));
        mockUser.setPreferredGenre(List.of(Genre.ROCK));
        
        UserResponse mockResponse = new UserResponse();
        mockResponse.setId("123");
        mockResponse.setName("Marcelo");
        mockResponse.setEmail("marcelo@gmail.com");
        mockResponse.setFavoriteArtist(List.of("The Beatles"));
        mockResponse.setPreferredGenre(List.of(Genre.ROCK));

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
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.setName("Marcelo");
        createRequest.setEmail("marcelo@gmail.com");
        createRequest.setFavoriteArtist(List.of("The Beatles"));
        createRequest.setPreferredGenre(List.of(Genre.ROCK));
        
        User mockUser = new User();
        mockUser.setId(null);
        mockUser.setName("Marcelo");
        mockUser.setEmail("marcelo@gmail.com");
        mockUser.setFavoriteArtist(List.of("The Beatles"));
        mockUser.setPreferredGenre(List.of(Genre.ROCK));
        
        User savedUser = new User();
        savedUser.setId("123");
        savedUser.setName("Marcelo");
        savedUser.setEmail("marcelo@gmail.com");
        savedUser.setFavoriteArtist(List.of("The Beatles"));
        savedUser.setPreferredGenre(List.of(Genre.ROCK));
        
        UserResponse mockResponse = new UserResponse();
        mockResponse.setId("123");
        mockResponse.setName("Marcelo");
        mockResponse.setEmail("marcelo@gmail.com");
        mockResponse.setFavoriteArtist(List.of("The Beatles"));
        mockResponse.setPreferredGenre(List.of(Genre.ROCK));

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
    	 User mockUser = new User();
    	 mockUser.setId("123");
    	 mockUser.setName("Marcelo");
    	 mockUser.setEmail("marcelo@gmail.com");
    	 mockUser.setFavoriteArtist(List.of("The Beatles"));
    	 mockUser.setPreferredGenre(List.of(Genre.ROCK));
         
         UserResponse mockResponse = new UserResponse();
         mockResponse.setId("123");
         mockResponse.setName("Marcelo");
         mockResponse.setEmail("marcelo@gmail.com");
         mockResponse.setFavoriteArtist(List.of("The Beatles"));
         mockResponse.setPreferredGenre(List.of(Genre.ROCK));
         
         UserCreateRequest request = new UserCreateRequest();
         request.setName("Marcelo");
         request.setEmail("marcelo@gmail.com");
         request.setFavoriteArtist(List.of("The Beatles"));
         request.setPreferredGenre(List.of(Genre.ROCK));
    	 

         when(userServicePort.getUserById("123")).thenReturn(Mono.just(mockUser));
         when(userServicePort.updateUser(any(String.class), any(User.class))).thenReturn(Mono.just(mockUser));
         when(restMapper.toUser(any(UserCreateRequest.class))).thenReturn(mockUser);
         
         when(restMapper.toUserResponse(mockUser)).thenReturn(mockResponse);

         Mono<UserResponse> result = userController.updateUser("123", request);

         StepVerifier.create(result)
                 .expectNext(mockResponse)
                 .verifyComplete();

         verify(userServicePort).updateUser("123", mockUser);
         verify(restMapper).toUser(request);
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
    	
    	UserCreateRequest request = new UserCreateRequest();
    	request.setName("Marcelo");
    	request.setEmail("marcelo@gmail.com");
    	request.setFavoriteArtist(List.of("The Beatles"));
    	request.setPreferredGenre(List.of(Genre.ROCK));
        
        
        User mockUser = new User();
        mockUser.setId("123");
        mockUser.setName("Marcelo");
        mockUser.setEmail("marcelo@gmail.com");
        mockUser.setFavoriteArtist(List.of("The Beatles"));
        mockUser.setPreferredGenre(List.of(Genre.ROCK));
        
        
        UserResponse mockResponse = new UserResponse();
        mockResponse.setId("123");
        mockResponse.setName("Marcelo");
        mockResponse.setEmail("marcelo@gmail.com");
        mockResponse.setFavoriteArtist(List.of("The Beatles"));
        mockResponse.setPreferredGenre(List.of(Genre.ROCK));
        

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
        User user = new User();
        user.setId("123");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setFavoriteArtist(List.of());
        user.setPreferredGenre(List.of());

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
    
    /**
     * Test to update a mood user when the user exists.
     */
    @Test
    void testUpdateMood_UserExists() {   
        
    	 User mockUser = new User();
    	 mockUser.setId("123");
    	 mockUser.setName("Marcelo");
    	 mockUser.setEmail("marcelo@gmail.com");
    	 mockUser.setMood(Mood.EXERCISE);
    	 mockUser.setFavoriteArtist(List.of("The Beatles"));
         mockUser.setPreferredGenre(List.of(Genre.ROCK));

         
         UserResponse mockResponse = new UserResponse();
         mockResponse.setId("123");
         mockResponse.setName("Marcelo");
         mockResponse.setEmail("marcelo@gmail.com");
         mockUser.setMood(Mood.EXERCISE);
         mockResponse.setFavoriteArtist(List.of("The Beatles"));
         mockResponse.setPreferredGenre(List.of(Genre.ROCK));
         
         UpdateMoodRequest req = new UpdateMoodRequest();
         req.setMood(Mood.EXERCISE);
    	 
         when(userServicePort.getUserById("123")).thenReturn(Mono.just(mockUser));
         when(userServicePort.updateMood(any(String.class), any(Mood.class))).thenReturn(Mono.just(mockUser));
         when(restMapper.toUserResponse(mockUser)).thenReturn(mockResponse);

         Mono<UserResponse> result = userController.updateMood("123", req);

         StepVerifier.create(result)
                 .expectNext(mockResponse)
                 .verifyComplete();
  

         verify(userServicePort).updateMood("123", req.getMood());
         verify(restMapper).toUserResponse(mockUser);
    }
    
    /**
     * Test to update a mood user when the user does not exist.
     */
    @Test
    void testUpdateMood_UserDoesNotExist() {
    	when(userServicePort.getUserById("123")).thenReturn(Mono.error(new UserNotFoundException("User not found with ID: 123")));

        Mono<UserResponse> result = userController.getUserById("123");

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UserNotFoundException && throwable.getMessage().equals("User not found with ID: 123"))
                .verify();

        verify(userServicePort).getUserById("123");
    }

}
