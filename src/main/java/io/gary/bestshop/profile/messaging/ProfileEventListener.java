package io.gary.bestshop.profile.messaging;

import io.gary.bestshop.profile.messaging.event.profile.ProfileCreatedEvent;
import io.gary.bestshop.profile.messaging.event.profile.ProfileDeletedEvent;
import io.gary.bestshop.profile.messaging.event.profile.ProfileUpdatedEvent;
import io.gary.bestshop.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileEventListener {

    private final ProfileService profileService;

    @StreamListener(MessagingChannels.PROFILE_CREATED_INPUT)
    public void handleProfileCreatedEvent(ProfileCreatedEvent event) {

        log.info("Processing event: {}", event);

        profileService.enableProfile(event.getNewProfile().getUsername());
    }

    @StreamListener(MessagingChannels.PROFILE_UPDATED_INPUT)
    public void handleProfileUpdatedEvent(ProfileUpdatedEvent event) {
        log.info("Processing event: {}", event);
    }

    @StreamListener(MessagingChannels.PROFILE_DELETED_INPUT)
    public void handleProfileDeletedEvent(ProfileDeletedEvent event) {
        log.info("Processing event: {}", event);
    }
}
