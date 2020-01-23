package com.mappers;

import com.dtos.UserMapStructDTO;
import com.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "email", target = "emailAddress")
    })
    UserMapStructDTO userToUserMapStructDTO(User user);

    List<UserMapStructDTO> userToUserMapStructDTO(List<User> user);

}
