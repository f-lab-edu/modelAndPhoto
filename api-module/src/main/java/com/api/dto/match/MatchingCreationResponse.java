package com.api.dto.match;

import com.api.enums.MatchingStatus;

public class MatchingCreationResponse {

    private final String matchRequestId;
    private final MatchingStatus status;

    public MatchingCreationResponse(String matchRequestId, MatchingStatus status) {
        this.matchRequestId = matchRequestId;
        this.status = status;
    }

    public String getMatchRequestId() {
        return matchRequestId;
    }

    public MatchingStatus getStatus() {
        return status;
    }
}
