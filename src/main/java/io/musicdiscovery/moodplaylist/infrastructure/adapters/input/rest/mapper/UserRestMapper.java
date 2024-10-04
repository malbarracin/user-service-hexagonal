package io.musicdiscovery.user.infrastructure.adapters.input.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import io.musicdiscovery.user.domain.model.User;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.request.UserCreateRequest;
import io.musicdiscovery.user.infrastructure.adapters.input.rest.model.response.UserResponse;


/**
 * Mapper interface for converting between User and related DTOs.
 * This interface defines the mapping methods for transforming
 * UserCreateRequest into User, User into UserResponse, and
 * lists of Users into lists of UserResponses.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRestMapper {

    /**
     * Converts a UserCreateRequest object to a User domain object.
     *
     * @param request the UserCreateRequest object to convert
     * @return the converted User domain object
     */
    User toUser(UserCreateRequest request);

    /**
     * Converts a User domain object to a UserResponse object.
     *
     * @param user the User domain object to convert
     * @return the converted UserResponse object
     */
    UserResponse toUserResponse(User user);

    /**
     * Converts a list of User domain objects to a list of UserResponse objects.
     *
     * @param userList the list of User domain objects to convert
     * @return the list of converted UserResponse objects
     */
    List<UserResponse> toUserResponseList(List<User> userList);
}