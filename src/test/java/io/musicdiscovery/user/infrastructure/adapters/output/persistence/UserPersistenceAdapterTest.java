package io.musicdiscovery.user.infrastructure.adapters.output.persistence;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.domain.model.enums.Genre;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.entity.UserEntity;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserPersistenceAdapterTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserPersistenceMapper mapper;

    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test para el método findAll() que verifica si se devuelven todos los usuarios correctamente.
     */
    @Test
    void testFindAll_Success() {
        User user1 = new User();
        user1.setId("1");
        user1.setName("Marcelo");
        user1.setEmail("marcelo@gmail.com");
        user1.setFavoriteArtist(List.of("The Beatles"));
        user1.setPreferredGenre(List.of(Genre.ROCK));
        
        User user2 = new User();
        user2.setId("2");
        user2.setName("Juan");
        user2.setEmail("jjuan@gmail.com");
        user2.setFavoriteArtist(List.of("Adele"));
        user2.setPreferredGenre(List.of(Genre.POP));
        
        UserEntity entity1 = new UserEntity();
        entity1.setId("1");
        entity1.setName("Marcelo");
        entity1.setEmail("marcelo@gmail.com");
        entity1.setFavoriteArtist(List.of("The Beatles"));
        entity1.setPreferredGenre(List.of(Genre.ROCK));
        
        UserEntity entity2 = new UserEntity();
        entity2.setId("2");
        entity2.setName("Juan");
        entity2.setEmail("jjuan@gmail.com");
        entity2.setFavoriteArtist(List.of("Adele"));
        entity2.setPreferredGenre(List.of(Genre.POP));

        when(repository.findAll()).thenReturn(Flux.just(entity1, entity2));
        when(mapper.toUser(entity1)).thenReturn(user1);
        when(mapper.toUser(entity2)).thenReturn(user2);

        Mono<List<User>> result = userPersistenceAdapter.findAll();

        StepVerifier.create(result)
                .expectNext(Arrays.asList(user1, user2))
                .verifyComplete();

        verify(repository).findAll();
        verify(mapper, times(2)).toUser(any(UserEntity.class));
    }

    /**
     * Test para el método findAll() que verifica la situación en la que no hay usuarios.
     */
    @Test
    void testFindAll_EmptyList() {
        when(repository.findAll()).thenReturn(Flux.empty());

        Mono<List<User>> result = userPersistenceAdapter.findAll();

        StepVerifier.create(result)
                .expectNext(Collections.emptyList())
                .verifyComplete();

        verify(repository).findAll();
    }

    /**
     * Test para el método findById() que verifica si se puede obtener un usuario existente.
     */
    @Test
    void testFindById_Success() {
        User user = new User();
        user.setId("1");
        user.setName("Marcelo");
        user.setEmail("marcelo@gmail.com");
        user.setFavoriteArtist(List.of("The Beatles"));
        user.setPreferredGenre(List.of(Genre.ROCK));
        
        UserEntity entity = new UserEntity();
        entity.setId("1");
        entity.setName("Marcelo");
        entity.setEmail("marcelo@gmail.com");
        entity.setFavoriteArtist(List.of("The Beatles"));
        entity.setPreferredGenre(List.of(Genre.ROCK));
        

        when(repository.findById("1")).thenReturn(Mono.just(entity));
        when(mapper.toUser(entity)).thenReturn(user);

        Mono<User> result = userPersistenceAdapter.findById("1");

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(repository).findById("1");
        verify(mapper).toUser(entity);
    }

    /**
     * Test para el método findById() que verifica la situación en la que el usuario no existe.
     */
    @Test
    void testFindById_UserNotFound() {
        when(repository.findById("999")).thenReturn(Mono.empty());

        Mono<User> result = userPersistenceAdapter.findById("999");

        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).findById("999");
    }

    /**
     * Test para el método save() que verifica si se puede guardar un usuario correctamente.
     */
    @Test
    void testSave_Success() {
        User user = new User();
        user.setId("1");
        user.setName("Marcelo");
        user.setEmail("marcelo@gmail.com");
        user.setFavoriteArtist(List.of("The Beatles"));
        user.setPreferredGenre(List.of(Genre.ROCK));
        
        UserEntity entity = new UserEntity();
        entity.setId("1");
        entity.setName("Marcelo");
        entity.setEmail("marcelo@gmail.com");
        entity.setFavoriteArtist(List.of("The Beatles"));
        entity.setPreferredGenre(List.of(Genre.ROCK));

        when(mapper.toUserEntity(user)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.just(entity));
        when(mapper.toUser(entity)).thenReturn(user);

        Mono<User> result = userPersistenceAdapter.save(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(mapper).toUserEntity(user);
        verify(repository).save(entity);
    }

    /**
     * Test para el método save() que verifica la situación en la que se produce un error al guardar.
     */
    @Test
    void testSave_Error() {
        User user = new User();
        user.setId("1");
        user.setName("Marcelo");
        user.setEmail("marcelo@gmail.com");
        user.setFavoriteArtist(List.of("The Beatles"));
        user.setPreferredGenre(List.of(Genre.ROCK));
        
        UserEntity entity = new UserEntity();
        entity.setId("1");
        entity.setName("Marcelo");
        entity.setEmail("marcelo@gmail.com");
        entity.setFavoriteArtist(List.of("The Beatles"));
        entity.setPreferredGenre(List.of(Genre.ROCK));

        when(mapper.toUserEntity(user)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(Mono.error(new RuntimeException("Error al guardar")));

        Mono<User> result = userPersistenceAdapter.save(user);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(mapper).toUserEntity(user);
        verify(repository).save(entity);
    }

    /**
     * Test para el método deleteById() que verifica si se puede eliminar un usuario.
     */
    @Test
    void testDeleteById_Success() {
        when(repository.deleteById("1")).thenReturn(Mono.empty());

        Mono<Void> result = userPersistenceAdapter.deleteById("1");

        StepVerifier.create(result)
                .verifyComplete();

        verify(repository).deleteById("1");
    }

    /**
     * Test para el método deleteById() que verifica la situación en la que se intenta eliminar un usuario que no existe.
     */
    @Test
    void testDeleteById_UserNotFound() {
        when(repository.deleteById("999")).thenReturn(Mono.error(new RuntimeException("User not found")));

        Mono<Void> result = userPersistenceAdapter.deleteById("999");

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(repository).deleteById("999");
    }
}