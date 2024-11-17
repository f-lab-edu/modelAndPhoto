package com.api.message;

import java.time.LocalDateTime;

public class Message {

    private String messageId;
    private String senderId;
    private String receiverId;
    private String messageContent;
    private LocalDateTime timestamp;
    private boolean readStatus;

    public Message(String messageId, String senderId, String receiverId, String messageContent, LocalDateTime timestamp, boolean readStatus) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
        this.readStatus = readStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isReadStatus() {
        return readStatus;
    }
}
