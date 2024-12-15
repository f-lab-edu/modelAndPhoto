package com.api.dto.message;

import com.api.enums.MessageStatus;
import lombok.Getter;

@Getter
public class ConversationMessageStatusResponse {

    private final String conversationId;
    private final MessageStatus messageStatus;

    public ConversationMessageStatusResponse(String conversationId, MessageStatus messageStatus) {
        this.conversationId = conversationId;
        this.messageStatus = messageStatus;
    }

}
