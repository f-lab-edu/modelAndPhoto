package com.api.dto.match;

import com.api.enums.MatchingStatus;

public class MatchResponse {

    private final String matchRequestId;
    private final MatchingStatus status;

    public MatchResponse(String matchRequestId, MatchingStatus status) {
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
