package io.gary.bestshop.profile.messaging.event.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreatedEvent {

    ProfileDto newProfile;
}
