package io.gary.bestshop.profile.messaging;


import io.gary.bestshop.messaging.event.order.OrderCompletedEvent;
import io.gary.bestshop.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final ProfileService profileService;

    @StreamListener(MessagingChannels.ORDER_COMPLETED_INPUT)
    public void handleOrderCompletedEvent(OrderCompletedEvent event) {

        log.info("Processing event: {}", event);

        profileService.updatePurchaseStatus(event.getOrder().getPurchasedBy(), event.getOrder().getPrice());
    }
}
