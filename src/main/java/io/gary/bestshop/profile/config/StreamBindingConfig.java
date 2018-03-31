package io.gary.bestshop.profile.config;

import io.gary.bestshop.profile.messaging.MessagingChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(MessagingChannels.class)
public class StreamBindingConfig {

}
