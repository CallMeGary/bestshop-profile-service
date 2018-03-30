package io.gary.bestshop.profile.service;

import io.gary.bestshop.profile.domain.Profile;
import io.gary.bestshop.profile.errors.ProfileAlreadyExistsException;
import io.gary.bestshop.profile.errors.ProfileNotFoundException;
import io.gary.bestshop.profile.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static io.gary.bestshop.profile.domain.ProfileStatus.Pending;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;

    public List<Profile> getProfiles() {

        log.info("Getting profiles");

        return profileRepository.findAll();
    }

    public Profile getProfile(@NotNull String username) {

        log.info("Getting profile: username={}", username);

        return findByUsernameOrThrow(username);
    }

    public Profile createProfile(@NotNull @Valid Profile profile) {

        log.info("Creating profile: profile={}", profile);

        checkEmailNotExistsOrThrow(profile.getEmail());
        checkUsernameNotExistsOrThrow(profile.getUsername());

        LocalDateTime now = LocalDateTime.now();
        Profile toCreate = profile.withStatus(Pending).withCreatedAt(now).withLastModifiedAt(now);

        return profileRepository.save(toCreate);
    }

    public Profile updateProfile(@NotNull String username, @NotNull @Valid Profile profile) {

        log.info("Updating profile: username={}, profile={}", username, profile);

        Profile toUpdate = findByUsernameOrThrow(username);

        ofNullable(profile.getNickname()).ifPresent(toUpdate::setNickname);
        ofNullable(profile.getFirstName()).ifPresent(toUpdate::setFirstName);
        ofNullable(profile.getLastName()).ifPresent(toUpdate::setLastName);
        ofNullable(profile.getMobilePhone()).ifPresent(toUpdate::setMobilePhone);
        ofNullable(profile.getBirthDate()).ifPresent(toUpdate::setBirthDate);

        return profileRepository.save(toUpdate.withLastModifiedAt(LocalDateTime.now()));
    }

    public void deleteProfile(@NotNull String username) {

        log.info("Deleting profile: username={}", username);

        Profile toDelete = findByUsernameOrThrow(username);

        profileRepository.delete(toDelete);
    }

    private void checkUsernameNotExistsOrThrow(String username) {
        if (profileRepository.findByUsername(username).isPresent()) {
            throw new ProfileAlreadyExistsException(username);
        }
    }

    private void checkEmailNotExistsOrThrow(String email) {
        if (profileRepository.findByEmail(email).isPresent()) {
            throw new ProfileAlreadyExistsException(email);
        }
    }

    private Profile findByUsernameOrThrow(String username) {
        return profileRepository.findByUsername(username).orElseThrow(() -> new ProfileNotFoundException(username));
    }

}
