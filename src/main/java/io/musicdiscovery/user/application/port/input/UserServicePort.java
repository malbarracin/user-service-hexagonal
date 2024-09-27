package io.musicdiscovery.user.application.port.input;

import java.util.List;

import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.domain.model.enums.Mood;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the input port for managing user-related operations
 * in the application, following the hexagonal architecture pattern. It exposes
 * methods for creating, retrieving, updating, and deleting users.
 */
public interface UserServicePort {

    /**
     * Retrieve a user by their unique identifier.
     *
     * @param id the unique identifier of the user to retrieve
     * @return a {@link Mono} containing the user if found, or empty if not found
     */
    Mono<User> getUserById(String id);

    /**
     * Create a new user in the system.
     *
     * @param user the {@link User} entity to be created
     * @return a {@link Mono} containing the created user
     */
    Mono<User> createUser(User user);

    /**
     * Update an existing user's information.
     *
     * @param id the unique identifier of the user to update
     * @param user the updated {@link User} entity
     * @return a {@link Mono} containing the updated user, or empty if the user was not found
     */
    Mono<User> updateUser(String id, User user);

    /**
     * Retrieve all users from the system.
     *
     * @return a {@link Flux} containing all users in the system
     */
    Mono<List<User>> getAllUsers();

    /**
     * Delete a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete
     * @return a {@link Mono} signaling completion when the user is deleted
     */
    Mono<Void> deleteUser(String id);
    
    /**
     * Updates the mood of a user's profile.
     *
     * @param id   The ID of the user profile whose mood is being updated.
     * @param mood The new mood to set for the user's profile.
     * @return A {@link Mono} containing the updated {@link UserProfile}, or empty if the profile does not exist.
     */
    Mono<User> updateMood(String id, Mood mood);
}
