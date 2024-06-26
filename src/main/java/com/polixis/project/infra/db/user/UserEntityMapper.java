package com.polixis.project.infra.db.user;

import com.polixis.project.core.user.User;
import com.polixis.project.core.user.UserSaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    User map(UserEntity userEntity);
    UserEntity map(UserSaveRequest user);
}
