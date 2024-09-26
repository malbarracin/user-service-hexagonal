package io.musicdiscovery.user.infrastructure.adapters.output.persistence;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import io.musicdiscovery.user.application.port.output.UserPersistencePort;
import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.response.UserResponse;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.entity.UserEntity;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

	private final UserRepository repository;
	private final UserPersistenceMapper mapper;

	@Override
	public Mono<List<User>> findAll() {
		return repository.findAll()
	            .map(mapper::toUser)
	            .collectList();
	}
	
	
	@Override
	public Mono<User> findById(String id) {
		return repository.findById(id).map(mapper::toUser);
	}

	@Override
	public Mono<User> save(User user) {
		UserEntity entity = mapper.toUserEntity(user);
		return repository.save(entity).map(mapper::toUser);
	}

	@Override
	public Mono<Void> deleteById(String userId) {
		return repository.deleteById(userId);
	}

}
