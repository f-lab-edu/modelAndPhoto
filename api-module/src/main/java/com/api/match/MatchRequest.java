package com.api.match;

public class MatchRequest {

    private final int senderId;
    private final int receiverId;

    public MatchRequest(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
