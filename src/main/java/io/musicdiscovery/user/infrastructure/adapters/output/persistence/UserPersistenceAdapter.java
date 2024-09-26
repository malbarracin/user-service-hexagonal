package io.musicdiscovery.user.infrastructure.adapters.output.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import io.musicdiscovery.user.application.port.output.UserPersistencePort;
import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.entity.UserEntity;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Adapter class that implements the UserPersistencePort interface.
 * This class acts as a bridge between the domain layer and the persistence layer,
 * handling user-related data operations using the UserRepository.
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository repository;
    private final UserPersistenceMapper mapper;

    /**
     * Finds all users in the repository.
     *
     * @return a Mono containing a list of User domain objects.
     */
    @Override
    public Mono<List<User>> findAll() {
        return repository.findAll()
                .map(mapper::toUser)
                .collectList();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find
     * @return a Mono containing the User domain object if found, or an empty Mono if not found.
     */
    @Override
    public Mono<User> findById(String id) {
        return repository.findById(id).map(mapper::toUser);
    }

    /**
     * Saves a new user to the repository.
     *
     * @param user the User domain object to save
     * @return a Mono containing the saved User domain object.
     */
    @Override
    public Mono<User> save(User user) {
        UserEntity entity = mapper.toUserEntity(user);
        return repository.save(entity).map(mapper::toUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     * @return a Mono that completes when the deletion is done.
     */
    @Override
    public Mono<Void> deleteById(String userId) {
        return repository.deleteById(userId);
    }
}