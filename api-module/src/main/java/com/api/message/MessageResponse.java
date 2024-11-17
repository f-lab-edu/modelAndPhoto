package com.api.message;

import java.time.LocalDateTime;

public class MessageResponse {

    public String senderId;
    public String receiverId;
    public String message;
    public String fileId;
    public LocalDateTime timestamp;
    public MessageStatus status;

    public MessageResponse(String senderId, String receiverId, String message, String fileId, LocalDateTime timestamp, MessageStatus status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.fileId = fileId;
        this.timestamp = timestamp;
        this.status = status;
    }
}
