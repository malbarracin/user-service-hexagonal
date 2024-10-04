package io.musicdiscovery.user.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import io.musicdiscovery.user.infrastructure.adapters.output.persistence.entity.UserEntity;

/**
 * Adapter for interacting with MongoDB through ReactiveMongoRepository.
 * Implements the UserRepositoryPort interface to serve as the output adapter
 * in the hexagonal architecture.
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {

   
}