package com.polixis.project.core.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private IUserService userService;

    @Mock
    UserPersistenceManager userPersistenceManager;

    @BeforeEach
    void setUp() {
        userService = new UserService(userPersistenceManager);
    }

    @Test
    void should_delegate_to_persistence_manager_when_saving_user() {
        UserSaveRequest request = mock(UserSaveRequest.class);
        userService.saveUser(request);
        verify(userPersistenceManager).save(request);
    }

    @Test
    void should_delegate_to_persistence_manager_when_listing_users() {
        userService.getAllUsers();
        verify(userPersistenceManager).getAllUsers();
    }
}