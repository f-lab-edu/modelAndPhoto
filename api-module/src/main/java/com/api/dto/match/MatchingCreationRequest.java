package com.api.dto.match;

public class MatchingCreationRequest {

    private String senderId;
    private String receiverId;
    private String message;

    public MatchingCreationRequest(String senderId, String receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }
}
