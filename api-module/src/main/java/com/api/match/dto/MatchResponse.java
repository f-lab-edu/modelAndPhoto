package com.api.match.dto;

import com.api.match.enums.MatchingStatus;

public class MatchResponse {

    private final MatchingStatus status;

    public MatchResponse(MatchingStatus status) {
        this.status = status;
    }

    public MatchingStatus getStatus() {
        return status;
    }
}
