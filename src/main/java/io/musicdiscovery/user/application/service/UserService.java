package io.musicdiscovery.user.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.musicdiscovery.user.application.port.input.UserServicePort;
import io.musicdiscovery.user.application.port.output.UserPersistencePort;
import io.musicdiscovery.user.domain.exception.UserNotFoundException;
import io.musicdiscovery.user.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

/**
 * Service class for managing users.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserServicePort {

	private final UserPersistencePort userRepository;

	/**
	 * Retrieves a user by their ID.
	 *
	 * @param id the ID of the user
	 * @return a {@link Mono} containing the user if found
	 * @throws UserNotFoundException if the user is not found
	 */
	@Override
	public Mono<User> getUserById(String id) {
		return userRepository.findById(id)
				.switchIfEmpty(Mono.error(new UserNotFoundException("User not found with ID: " + id)));
	}

	/**
	 * Creates a new user.
	 *
	 * @param user the user to create
	 * @return a {@link Mono} containing the created user
	 */
	@Override
	public Mono<User> createUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Updates an existing user.
	 *
	 * @param id   the ID of the user to update
	 * @param user the updated user information
	 * @return a {@link Mono} containing the updated user
	 * @throws UserNotFoundException if the user is not found
	 */
	@Override
	public Mono<User> updateUser(String id, User user) {
		return userRepository.findById(id).flatMap(existingUser -> {
			Optional.ofNullable(user.getName()).filter(name -> !name.isEmpty()).ifPresent(existingUser::setName);
			Optional.ofNullable(user.getEmail()).filter(name -> !name.isEmpty()).ifPresent(existingUser::setEmail);
			Optional.ofNullable(user.getFavoriteArtist()).filter(name -> !name.isEmpty())
					.ifPresent(existingUser::setFavoriteArtist);
			Optional.ofNullable(user.getPreferredGenre()).filter(name -> !name.isEmpty())
					.ifPresent(existingUser::setPreferredGenre);
			return userRepository.save(existingUser);
		}).switchIfEmpty(Mono.error(new UserNotFoundException("User not found with ID: " + id)));
	}

	/**
	 * Retrieves all users.
	 *
	 * @return a {@link Flux} containing all users
	 */
	@Override
	public Mono<List<User>> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Deletes a user by their ID.
	 *
	 * @param id the ID of the user to delete
	 * @return a {@link Mono} that completes when the user is deleted
	 * @throws UserNotFoundException if the user is not found
	 */
	@Override
	public Mono<Void> deleteUser(String id) {

		if (userRepository.findById(id) == null) {
			throw new UserNotFoundException("User not found with ID: " + id);
		}

		return userRepository.deleteById(id);

	}
}