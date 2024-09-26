package io.musicdiscovery.user.infrastructure.adapters.output.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.infrastructure.adapters.output.persistence.entity.UserEntity;



@Mapper(componentModel = "spring")
public interface  UserPersistenceMapper {

	UserEntity toUserEntity(User user);
	
	User toUser(UserEntity entity);
	
	List<User> toUserList(List<UserEntity> entityList);
	
}
