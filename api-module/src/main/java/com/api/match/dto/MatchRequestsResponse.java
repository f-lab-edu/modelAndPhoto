package com.api.match.dto;

import java.util.List;

public class MatchRequestsResponse {

    private final List<MatchingRequest> requests;

    public MatchRequestsResponse(List<MatchingRequest> requests) {
        this.requests = requests;
    }

    public List<MatchingRequest> getRequests() {
        return requests;
    }
}
