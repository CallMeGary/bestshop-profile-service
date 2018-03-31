package io.gary.bestshop.profile.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MessagingChannels {

    String PROFILE_CREATED_OUTPUT = "profileCreatedOutput";
    @Output(PROFILE_CREATED_OUTPUT)
    MessageChannel profileCreatedOutput();


    String PROFILE_UPDATED_OUTPUT = "profileUpdatedOutput";
    @Output(PROFILE_UPDATED_OUTPUT)
    MessageChannel profileUpdatedOutput();


    String PROFILE_DELETED_OUTPUT = "profileDeletedOutput";
    @Output(PROFILE_DELETED_OUTPUT)
    MessageChannel profileDeletedOutput();


    String PROFILE_CREATED_INPUT = "profileCreatedInput";
    @Input(PROFILE_CREATED_INPUT)
    MessageChannel profileCreatedInput();


    String PROFILE_UPDATED_INPUT = "profileUpdatedInput";
    @Input(PROFILE_UPDATED_INPUT)
    MessageChannel profileUpdatedInput();


    String PROFILE_DELETED_INPUT = "profileDeletedInput";
    @Input(PROFILE_DELETED_INPUT)
    MessageChannel profileDeletedInput();


    String ORDER_COMPLETED_INPUT = "orderCompletedInput";
    @Input(ORDER_COMPLETED_INPUT)
    MessageChannel orderCompletedInput();

}
