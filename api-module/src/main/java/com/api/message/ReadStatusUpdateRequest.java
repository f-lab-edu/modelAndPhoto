package com.api.message;

public class ReadStatusUpdateRequest {

    private String readerId;

    public ReadStatusUpdateRequest() {}

    public ReadStatusUpdateRequest(String readerId) {
        this.readerId = readerId;
    }

    public String getReaderId() {
        return readerId;
    }
}
