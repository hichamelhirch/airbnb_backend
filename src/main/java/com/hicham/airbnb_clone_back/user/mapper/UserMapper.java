package com.hicham.airbnb_clone_back.user.mapper;

import com.hicham.airbnb_clone_back.user.Domain.Authority;
import com.hicham.airbnb_clone_back.user.Domain.User;
import com.hicham.airbnb_clone_back.user.application.dto.ReadUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ReadUserDTO readUserDtoToUser(User user);
    default String mapAuthoritiesToString(Authority authority){
        return authority.getName();
    }
}
