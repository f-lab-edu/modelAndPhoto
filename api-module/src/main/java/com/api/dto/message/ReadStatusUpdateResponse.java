package com.api.dto.message;

import com.api.enums.MessageStatus;

import java.time.LocalDateTime;

public class ReadStatusUpdateResponse {

    private final String conversationId;
    private final MessageStatus status;
    private final LocalDateTime timestamp;

    public ReadStatusUpdateResponse(ConversationMessageStatusResponse conversationMessageStatusResponse, LocalDateTime timestamp) {
        this.conversationId = conversationMessageStatusResponse.getConversationId();
        this.status = conversationMessageStatusResponse.getMessageStatus();
        this.timestamp = timestamp;
    }

    public String getConversationId() {
        return conversationId;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
