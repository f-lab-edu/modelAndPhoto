package com.api.match.dto;

import com.api.match.enums.MatchingStatus;

public class MatchRespondRequest {

    private String requestId;
    private MatchingStatus matchingStatus;

    public MatchRespondRequest(String requestId, MatchingStatus matchingStatus) {
        this.requestId = requestId;
        this.matchingStatus = matchingStatus;
    }

    public String getRequestId() {
        return requestId;
    }

    public MatchingStatus getMatchingStatus() {
        return matchingStatus;
    }
}
