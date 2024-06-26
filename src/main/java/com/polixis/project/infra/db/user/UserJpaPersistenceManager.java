package com.polixis.project.infra.db.user;

import com.polixis.project.core.user.User;
import com.polixis.project.core.user.UserPersistenceManager;
import com.polixis.project.core.user.UserSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserJpaPersistenceManager implements UserPersistenceManager {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    public User save(UserSaveRequest user) {
        return userEntityMapper.map(userRepository.save(userEntityMapper.map(user)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().map(userEntityMapper::map).toList();
    }
}
