package com.polixis.project.infra.kafka.user;

import lombok.Builder;

@Builder
public record UserEvent (String firstName, String lastName) {
}
