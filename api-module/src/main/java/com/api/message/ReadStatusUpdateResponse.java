package com.api.message;

import java.time.LocalDateTime;

public class ReadStatusUpdateResponse {

    private final String conversationId;
    private final MessageStatus status;
    private final LocalDateTime timestamp;

    public ReadStatusUpdateResponse(String conversationId, MessageStatus status, LocalDateTime timestamp) {
        this.conversationId = conversationId;
        this.status = status;
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
