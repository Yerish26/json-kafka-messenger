package com.polixis.project.infra.kafka.user;

import com.polixis.project.core.user.IUserService;
import com.polixis.project.core.user.UserSaveRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@EnableKafka
@Testcontainers
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@SpringBootTest(classes = {
        KafkaUserListener.class,
        KafkaConsumerConfig.class,
        KafkaProducerConfig.class,
        UserEventMapper.class,
        UserEventMapperImpl.class
})
@ExtendWith(MockitoExtension.class)
class KafkaUserListenerIT {

    @Container
    private static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"))
                    .withEnv("KAFKA_CREATE_TOPICS", "test");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    KafkaTemplate<String, UserEvent> kafkaTemplate;

    @MockBean
    IUserService userService;

    @Captor
    ArgumentCaptor<UserSaveRequest> userSaveRequestCaptor;

    @Test
    void should_trigger_listener_upon_sending_an_event() {
        UserEvent userEvent = UserEvent.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        kafkaTemplate.send("test", userEvent);

        verify(userService, timeout(5000L)).saveUser(userSaveRequestCaptor.capture());

        assertThat(userSaveRequestCaptor.getValue())
                .hasFieldOrPropertyWithValue("firstName", "John")
                .hasFieldOrPropertyWithValue("lastName", "Doe");
    }
}