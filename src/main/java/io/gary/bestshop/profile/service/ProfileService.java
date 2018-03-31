package io.gary.bestshop.profile.service;

import io.gary.bestshop.profile.domain.Profile;
import io.gary.bestshop.profile.errors.ProfileAlreadyExistsException;
import io.gary.bestshop.profile.errors.ProfileNotFoundException;
import io.gary.bestshop.profile.messaging.ProfileEventPublisher;
import io.gary.bestshop.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static io.gary.bestshop.profile.domain.ProfileStatus.Enabled;
import static io.gary.bestshop.profile.domain.ProfileStatus.Pending;
import static java.math.BigDecimal.ZERO;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileEventPublisher profileEventPublisher;

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

        Profile createdProfile = profileRepository.save(toCreate);

        return profileEventPublisher.publishProfileCreatedEvent(createdProfile);
    }

    public Profile updateProfile(@NotNull String username, @NotNull @Valid Profile profile) {

        log.info("Updating profile: username={}, profile={}", username, profile);

        Profile existingProfile = findByUsernameOrThrow(username);

        Profile toUpdate = existingProfile.withLastModifiedAt(LocalDateTime.now());
        ofNullable(profile.getNickname()).ifPresent(toUpdate::setNickname);
        ofNullable(profile.getFirstName()).ifPresent(toUpdate::setFirstName);
        ofNullable(profile.getLastName()).ifPresent(toUpdate::setLastName);
        ofNullable(profile.getMobilePhone()).ifPresent(toUpdate::setMobilePhone);
        ofNullable(profile.getBirthDate()).ifPresent(toUpdate::setBirthDate);

        Profile updatedProfile = profileRepository.save(toUpdate);

        return profileEventPublisher.publishProfileUpdatedEvent(existingProfile, updatedProfile);
    }

    public void deleteProfile(@NotNull String username) {

        log.info("Deleting profile: username={}", username);

        Profile profile = findByUsernameOrThrow(username);

        profileRepository.delete(profile);

        profileEventPublisher.publishProfileDeletedEvent(profile);
    }

    public void enableProfile(@NotNull String username) {

        log.info("Enabling profile: username={}", username);

        Profile profile = findByUsernameOrThrow(username);

        profileRepository.save(profile.withStatus(Enabled));
    }

    public void updatePurchaseStatus(@NotNull String username, @NotNull BigDecimal price) {

        log.info("Updating purchase status: username={}, price={}", username, price);

        Profile profile = findByUsernameOrThrow(username);

        Integer currentPurchaseCount = ofNullable(profile.getPurchaseCount()).orElse(0);
        BigDecimal currentPurchaseAmount = ofNullable(profile.getPurchaseAmount()).orElse(ZERO);

        profileRepository.save(profile
                .withPurchaseCount(currentPurchaseCount + 1)
                .withPurchaseAmount(currentPurchaseAmount.add(price))
        );
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
