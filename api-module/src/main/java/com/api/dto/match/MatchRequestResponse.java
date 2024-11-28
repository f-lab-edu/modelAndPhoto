package com.api.dto.match;

import com.api.enums.MatchingStatus;

public class MatchRequestResponse {

    private final String matchRequestId;
    private final MatchingStatus status;

    public MatchRequestResponse(String matchRequestId, MatchingStatus status) {
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
