package com.polixis.project.api.user;

import com.polixis.project.core.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserApiMapper {
    UserApiModel map(User user);
}
