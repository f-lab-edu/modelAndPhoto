package com.api.match.dto;

public class MatchingCreationRequest {

    private String senderId;
    private String receiverId;

    public MatchingCreationRequest(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}
