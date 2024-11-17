package com.api.match;

public class MatchRespondRequest {

    private String requestId;
    private String response; // accepted or rejected


    public MatchRespondRequest(String requestId, String response) {
        this.requestId = requestId;
        this.response = response;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getResponse() {
        return response;
    }
}
