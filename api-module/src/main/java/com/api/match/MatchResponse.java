package com.api.match;

public class MatchResponse {

    private final String status;

    public MatchResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
