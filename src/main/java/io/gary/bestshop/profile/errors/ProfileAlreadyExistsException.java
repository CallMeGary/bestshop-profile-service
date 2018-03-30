package io.gary.bestshop.profile.errors;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class ProfileAlreadyExistsException extends RuntimeException {

    public ProfileAlreadyExistsException(String username) {
        super(String.format("Profile already exists with username=%s", username));
    }
}
