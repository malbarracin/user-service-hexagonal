package io.musicdiscovery.user.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request.UserCreateRequest;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.response.UserResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRestMapper {

	User toUser(UserCreateRequest request);

	UserResponse toUserResponse(User user);

	List<UserResponse> toUserResponseList(List<User> userList);
	

}