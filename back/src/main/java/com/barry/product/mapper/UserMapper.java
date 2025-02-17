package com.barry.product.mapper;

import com.barry.product.dto.request.UserRequest;
import com.barry.product.dto.response.UserResponse;
import com.barry.product.modele.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Convertit UserRequest en User (pour sauvegarde)
    @Mapping(target = "id", ignore = true) // ID géré par la base
    @Mapping(target = "password", ignore = true) // Hashé ailleurs
    User toUser(UserRequest userRequest);

    // Convertit User en UserResponse (pour réponse API)
    UserResponse toUserResponse(User user);
}
