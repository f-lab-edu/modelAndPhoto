package com.api.dto.match;

import com.api.enums.MatchingStatus;

public class MatchResponse {

    private final MatchingStatus status;

    public MatchResponse(MatchingStatus status) {
        this.status = status;
    }

    public MatchingStatus getStatus() {
        return status;
    }
}
