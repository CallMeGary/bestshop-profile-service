package io.gary.bestshop.profile.messaging;

import io.gary.bestshop.messaging.event.profile.ProfileCreatedEvent;
import io.gary.bestshop.messaging.event.profile.ProfileDeletedEvent;
import io.gary.bestshop.messaging.event.profile.ProfileUpdatedEvent;
import io.gary.bestshop.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileEventListener {

    private final static long ONE_MINUTE = 60L * 1000;

    private final TaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());

    private final ProfileService profileService;

    @StreamListener(MessagingChannels.PROFILE_CREATED_INPUT)
    public void handleProfileCreatedEvent(ProfileCreatedEvent event) {

        log.info("Processing event: {}", event);

        Date inOneMinute = new Date(System.currentTimeMillis() + ONE_MINUTE);

        scheduler.schedule(() -> profileService.enableProfile(event.getNewProfile().getUsername()), inOneMinute);
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
