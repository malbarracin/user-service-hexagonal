package io.musicdiscovery.user.application.port.output;

import java.util.List;

import io.musicdiscovery.user.domain.model.User;
import reactor.core.publisher.Mono;


/**
 * Port interface for the user repository.
 * This interface defines the contract for user-related persistence operations.
 */
public interface UserPersistencePort {
	 /**
     * Retrieves all users from the database.
     *
     * @return a Flux of User entities
     */
	Mono<List<User>> findAll();

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return a Mono containing the User if found, or empty if not found
     */
    Mono<User> findById(String id);

    /**
     * Saves a user entity to the database.
     *
     * @param user the User entity to save
     * @return a Mono containing the saved User
     */
    Mono<User> save(User user);

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @return a Mono signaling when the deletion is complete
     */
    Mono<Void> deleteById(String id);
}
