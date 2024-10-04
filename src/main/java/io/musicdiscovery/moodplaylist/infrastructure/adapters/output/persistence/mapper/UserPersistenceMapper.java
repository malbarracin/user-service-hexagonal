package io.musicdiscovery.user.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.entity.UserEntity;



/**
 * Mapper interface for converting between User domain objects and UserEntity objects.
 * This interface is implemented by MapStruct at compile time to provide the necessary 
 * mappings between the two types.
 */
@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    /**
     * Converts a User domain object to a UserEntity object.
     *
     * @param user the User domain object to convert
     * @return the corresponding UserEntity object
     */
    UserEntity toUserEntity(User user);

    /**
     * Converts a UserEntity object to a User domain object.
     *
     * @param entity the UserEntity object to convert
     * @return the corresponding User domain object
     */
    User toUser(UserEntity entity);

    /**
     * Converts a list of UserEntity objects to a list of User domain objects.
     *
     * @param entityList the list of UserEntity objects to convert
     * @return the list of corresponding User domain objects
     */
    List<User> toUserList(List<UserEntity> entityList);
}