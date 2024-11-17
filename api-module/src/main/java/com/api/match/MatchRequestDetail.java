package com.api.match;

public class MatchRequestDetail {
    private final String requestId;
    private final String senderName;
    private final String senderRole;
    private final String status;

    public MatchRequestDetail(String requestId, String senderName, String senderRole, String status) {
        this.requestId = requestId;
        this.senderName = senderName;
        this.senderRole = senderRole;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public String getStatus() {
        return status;
    }
}
