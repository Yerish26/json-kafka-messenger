package com.polixis.project.core.user;

import lombok.Builder;

import java.util.UUID;

@Builder
public record User (UUID id, String firstName, String lastName) {
}
