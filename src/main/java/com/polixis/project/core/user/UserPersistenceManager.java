package com.polixis.project.core.user;

import java.util.List;

public interface UserPersistenceManager {
    User save(UserSaveRequest user);
    List<User> getAllUsers();
}
