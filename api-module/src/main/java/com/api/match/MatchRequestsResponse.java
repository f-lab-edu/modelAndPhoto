package com.api.match;

import java.util.List;

public class MatchRequestsResponse {

    private List<MatchRequestDetail> requests;

    public MatchRequestsResponse(List<MatchRequestDetail> requests) {
        this.requests = requests;
    }

    public List<MatchRequestDetail> getRequests() {
        return requests;
    }
}
