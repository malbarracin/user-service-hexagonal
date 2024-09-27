package io.musicdiscovery.user.infrastructure.adapters.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.musicdiscovery.user.application.port.input.UserServicePort;
import io.musicdiscovery.user.domain.exception.UserNotFoundException;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request.UpdateMoodRequest;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request.UserCreateRequest;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


/**
 * Controller class that handles HTTP requests related to user operations.
 * This class acts as an entry point (adapter) in the hexagonal architecture.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User API", description = "Operations related to user management")
public class UserController {

    private final UserServicePort userServicePort;
    private final UserRestMapper restMapper;
    
    @Operation(summary = "Get a user by ID", description = "Retrieve a user by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"id\": \"66f56b94831c3d47cc76cb54\",\r\n"
                            		+ "    \"name\": \"Marcelo Alejandro Albarracín\",\r\n"
                            		+ "    \"email\": \"marceloalejandro.albarracin@gmail.com\",\r\n"
                            		+ "    \"preferredGenre\": [\r\n"
                            		+ "        \"ROCK\",\r\n"
                            		+ "        \"JAZZ\"\r\n"
                            		+ "    ],\r\n"
                            		+ "    \"favoriteArtist\": [\r\n"
                            		+ "        \"The Beatles\",\r\n"
                            		+ "        \"Miles Davis\"\r\n"
                            		+ "    ]\r\n"
                            		+ "}"))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\":\"User not found with ID: 123\", \"status\": 404}")))
    })
    @GetMapping("/{id}")
    public Mono<UserResponse> getUserById(@PathVariable String id) {
    	return userServicePort.getUserById(id)
    	        .map(restMapper::toUserResponse)
    	        .switchIfEmpty(Mono.error(new UserNotFoundException(id)));
        
    }

    @Operation(summary = "Create a new user", description = "Create a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"id\": \"66f56b94831c3d47cc76cb54\",\r\n"
                            		+ "    \"name\": \"Marcelo Alejandro Albarracín\",\r\n"
                            		+ "    \"email\": \"marceloalejandro.albarracin@gmail.com\",\r\n"
                            		+ "    \"preferredGenre\": [\r\n"
                            		+ "        \"ROCK\",\r\n"
                            		+ "        \"JAZZ\"\r\n"
                            		+ "    ],\r\n"
                            		+ "    \"favoriteArtist\": [\r\n"
                            		+ "        \"The Beatles\",\r\n"
                            		+ "        \"Miles Davis\"\r\n"
                            		+ "    ]\r\n"
                            		+ "}"))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                    		schema = @Schema(implementation = UserCreateRequest.class),
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"message\": \"Invalid request input: Failed to read HTTP message\",\r\n"
                            		+ "    \"status\": 400\r\n"
                            		+ "}")))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> createUser(@Valid @RequestBody UserCreateRequest user) {
    	return userServicePort.createUser(restMapper.toUser(user))
    	        .map(restMapper::toUserResponse);
    }

    @Operation(summary = "Update an existing user", description = "Update the details of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserCreateRequest.class),
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"id\": \"66f56b94831c3d47cc76cb54\",\r\n"
                            		+ "    \"name\": \"Marcelo Albarracín\",\r\n"
                            		+ "    \"email\": \"albarracin@gmail.com\",\r\n"
                            		+ "    \"preferredGenre\": [\r\n"
                            		+ "        \"ROCK\"\r\n"
                            		+ "    ],\r\n"
                            		+ "    \"favoriteArtist\": [\r\n"
                            		+ "        \"The Beatles\"\r\n"
                            		+ "    ]\r\n"
                            		+ "}"))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"message\": \"User not found with ID: 66f4bdf541bae35a29ecd68f2\",\r\n"
                            		+ "    \"status\": 404\r\n"
                            		+ "}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\r\n"
                    		+ "    \"message\": \"Invalid request input: Failed to read HTTP message\",\r\n"
                    		+ "    \"status\": 400\r\n"
                    		+ "}")))
            
    })
    @PutMapping("/{id}")
    public Mono<UserResponse> updateUser(@PathVariable String id, @Valid @RequestBody UserCreateRequest user) {       
        return userServicePort.updateUser(id, restMapper.toUser(user))
                .map(restMapper::toUserResponse);
        
    }

    @Operation(summary = "Delete a user", description = "Delete a user by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"message\": \"User not found with ID: 66f4bdf541bae35a29ecd68f\",\r\n"
                            		+ "    \"status\": 404\r\n"
                            		+ "}")))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable String id) {
    	//return userServicePort.deleteUser(id);
    	 return userServicePort.getUserById(id)
    		        .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with ID: " + id)))
    		        .flatMap(user -> userServicePort.deleteUser(id));
    }

    @Operation(summary = "Get all users", description = "Retrieve all users from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = "[\r\n"
                            		+ "    {\r\n"
                            		+ "        \"id\": \"66f56b94831c3d47cc76cb54\",\r\n"
                            		+ "        \"name\": \"Marcelo Alejandro Albarracín\",\r\n"
                            		+ "        \"email\": \"marceloalejandro.albarracin@gmail.com\",\r\n"
                            		+ "        \"preferredGenre\": [\r\n"
                            		+ "            \"ROCK\",\r\n"
                            		+ "            \"JAZZ\"\r\n"
                            		+ "        ],\r\n"
                            		+ "        \"favoriteArtist\": [\r\n"
                            		+ "            \"The Beatles\",\r\n"
                            		+ "            \"Miles Davis\"\r\n"
                            		+ "        ]\r\n"
                            		+ "    }\r\n"
                            		+ "]"))})
    })
    
    @GetMapping
    public Mono<List<UserResponse>> getAllUsers() {
    	return userServicePort.getAllUsers()
                .map(restMapper::toUserResponseList);
    }
    
    @Operation(summary = "Update mood an existing user", description = "Update the mood of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User mood updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class),
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"id\": \"66f6c16114bc0440df633f97\",\r\n"
                            		+ "    \"name\": \"Marcelo Alejandro Albarracín\",\r\n"
                            		+ "    \"email\": \"marceloalejandro.albarracin@gmail.com\",\r\n"
                            		+ "    \"mood\": \"MOTIVATED\",\r\n"
                            		+ "    \"preferredGenre\": [\r\n"
                            		+ "        \"ROCK\",\r\n"
                            		+ "        \"JAZZ\"\r\n"
                            		+ "    ],\r\n"
                            		+ "    \"favoriteArtist\": [\r\n"
                            		+ "        \"The Beatles\",\r\n"
                            		+ "        \"Miles Davis\"\r\n"
                            		+ "    ]\r\n"
                            		+ "}"))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\r\n"
                            		+ "    \"message\": \"User not found with ID: 66f4bdf541bae35a29ecd68f2\",\r\n"
                            		+ "    \"status\": 404\r\n"
                            		+ "}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\r\n"
                    		+ "    \"message\": \"Invalid request input: Failed to read HTTP message\",\r\n"
                    		+ "    \"status\": 400\r\n"
                    		+ "}")))
            
    })
    @PutMapping("/{id}/mood")
    public Mono<UserResponse> updateMood(@PathVariable String id, @RequestBody UpdateMoodRequest mood) {
        return userServicePort.updateMood(id, mood.getMood())
    	        .map(restMapper::toUserResponse);
        
    }
}