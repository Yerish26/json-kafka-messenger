package com.polixis.project.infra.kafka.user;

import com.polixis.project.core.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaUserListener {

    private final IUserService userService;
    private final UserEventMapper mapper;

    @KafkaListener(topics = "test", groupId = "groupId")
    public void listenUser(UserEvent userEvent) {
        log.info("Received User Event: {}", userEvent);
        userService.saveUser(mapper.map(userEvent));
    }
}
