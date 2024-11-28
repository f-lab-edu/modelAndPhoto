package com.api.dto.match;

import com.api.enums.MatchingStatus;
import com.api.enums.UserRole;

public class MatchingRequest {
    private final String requestId;
    private final String senderName;
    private final UserRole senderRole;
    private final MatchingStatus status;

    public MatchingRequest(String requestId, String senderName, UserRole senderRole, MatchingStatus status) {
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

    public UserRole getSenderRole() {
        return senderRole;
    }

    public MatchingStatus getStatus() {
        return status;
    }
}
