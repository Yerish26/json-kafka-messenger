package com.polixis.project.infra.kafka.user;

import com.polixis.project.core.user.User;
import com.polixis.project.core.user.UserSaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEventMapper {
    UserSaveRequest map(UserEvent userEvent);
}
