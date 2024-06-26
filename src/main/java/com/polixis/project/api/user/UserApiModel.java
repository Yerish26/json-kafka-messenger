package com.polixis.project.api.user;

import java.util.UUID;

public record UserApiModel(UUID id, String firstName, String lastName) {
}
