package com.polixis.project.core.user;

import lombok.Builder;

@Builder
public record UserSaveRequest (String firstName, String lastName) {
}
