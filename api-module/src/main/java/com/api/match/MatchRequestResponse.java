package com.api.match;

public class MatchRequestResponse {

    private String matchRequestId;
    private String status;

    public MatchRequestResponse(String matchRequestId, String status) {
        this.matchRequestId = matchRequestId;
        this.status = status;
    }

    public String getMatchRequestId() {
        return matchRequestId;
    }

    public String getStatus() {
        return status;
    }
}
