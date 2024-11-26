package com.api.message.dto;

import com.api.message.enums.MessageStatus;

import java.time.LocalDateTime;

public class MessageResponse {

    private final String messageId;
    private final String conversationId;
    private final String senderId;
    private final String receiverId;
    private final LocalDateTime timestamp;
    private final MessageStatus status;

    public MessageResponse(String messageId, String conversationId, String senderId, String receiverId, LocalDateTime timestamp, MessageStatus status) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MessageStatus getStatus() {
        return status;
    }
}

