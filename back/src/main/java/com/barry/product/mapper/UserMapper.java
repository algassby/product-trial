package com.barry.product.mapper;

import com.barry.product.dto.request.UserRequest;
import com.barry.product.dto.response.UserResponse;
import com.barry.product.modele.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true) // ID géré par la base
    @Mapping(target = "password", ignore = true) // Hashé ailleurs
    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
