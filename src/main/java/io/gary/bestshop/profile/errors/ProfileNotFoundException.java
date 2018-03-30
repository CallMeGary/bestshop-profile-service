package io.gary.bestshop.profile.errors;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(String username) {
        super(String.format("Profile not found with username=%s", username));
    }
}
