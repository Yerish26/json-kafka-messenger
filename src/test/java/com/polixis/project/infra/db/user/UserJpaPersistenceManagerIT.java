package com.polixis.project.infra.db.user;

import com.polixis.project.core.user.User;
import com.polixis.project.core.user.UserSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(classes = {
        UserEntityMapper.class,
        UserEntityMapperImpl.class,
        UserJpaPersistenceManager.class,
        UserRepository.class
})
@EnableJpaRepositories
@EnableAutoConfiguration
class UserJpaPersistenceManagerIT {

    @Container
    private static final MySQLContainer<?> mySQLContainer =
            new MySQLContainer<>(DockerImageName.parse("mysql:8.4.0"))
                    .withUsername("root")
                    .withPassword("password")
                    .withDatabaseName("json_kafka_consumer");

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    }

    @Autowired UserJpaPersistenceManager persistenceManager;
    @Autowired UserRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void should_save_user_in_database() {
        UserSaveRequest userSaveRequest = UserSaveRequest.builder()
                .firstName("John")
                .lastName("Peters")
                .build();

        User user = persistenceManager.save(userSaveRequest);

        assertThat(repository.findAll()).hasSize(1);
        assertThat(repository.findById(user.id()))
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("firstName", "John")
                .hasFieldOrPropertyWithValue("lastName", "Peters");
    }
}