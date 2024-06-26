package com.polixis.project.core.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserPersistenceManager persistenceManager;

    @Override
    public void saveUser(UserSaveRequest user) {
        persistenceManager.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return persistenceManager.getAllUsers();
    }
}
