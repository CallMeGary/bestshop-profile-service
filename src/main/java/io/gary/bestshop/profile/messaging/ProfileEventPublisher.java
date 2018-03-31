package io.gary.bestshop.profile.messaging;

import io.gary.bestshop.profile.domain.Profile;
import io.gary.bestshop.profile.messaging.event.profile.ProfileCreatedEvent;
import io.gary.bestshop.profile.messaging.event.profile.ProfileDeletedEvent;
import io.gary.bestshop.profile.messaging.event.profile.ProfileDto;
import io.gary.bestshop.profile.messaging.event.profile.ProfileUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static org.springframework.integration.support.MessageBuilder.withPayload;

@Component
@RequiredArgsConstructor
public class ProfileEventPublisher {

    private final MessagingChannels messagingChannels;

    public Profile publishProfileCreatedEvent(Profile profile) {
        messagingChannels.profileCreatedOutput().send(buildProfileCreatedEvent(profile));
        return profile;
    }

    public Profile publishProfileUpdatedEvent(Profile oldProfile, Profile updatedProfile) {
        messagingChannels.profileUpdatedOutput().send(buildProfileUpdatedEvent(oldProfile, updatedProfile));
        return updatedProfile;
    }

    public Profile publishProfileDeletedEvent(Profile profile) {
        messagingChannels.profileDeletedOutput().send(buildProfileDeletedEvent(profile));
        return profile;
    }

    private Message<?> buildProfileCreatedEvent(Profile profile) {
        return withPayload(new ProfileCreatedEvent(toDto(profile))).build();
    }

    private Message<?> buildProfileUpdatedEvent(Profile oldProfile, Profile updatedProfile) {
        return withPayload(new ProfileUpdatedEvent(toDto(oldProfile), toDto(updatedProfile))).build();
    }

    private Message<?> buildProfileDeletedEvent(Profile profile) {
        return withPayload(new ProfileDeletedEvent(toDto(profile))).build();
    }

    private ProfileDto toDto(Profile profile) {
        return ProfileDto.builder()
                .username(profile.getUsername())
                .email(profile.getEmail())
                .nickname(profile.getNickname())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .birthDate(profile.getBirthDate())
                .mobilePhone(profile.getMobilePhone())
                .createdAt(profile.getCreatedAt())
                .lastModifiedAt(profile.getLastModifiedAt())
                .build();
    }
}
