package com.polixis.project.core.user;

import java.util.List;

public interface IUserService {
    void saveUser(UserSaveRequest user);
    List<User> getAllUsers();
}
