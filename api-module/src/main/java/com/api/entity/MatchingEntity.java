package com.api.entity;

import com.api.enums.MatchingStatus;

import java.time.LocalDateTime;

public class MatchingEntity {

    private String matchingId; // Primary Key
    private String senderId; // 요청을 보낸 사용자 ID
    private String senderName; // 요청을 보낸 사용자 이름
    private String receiverId; // 요청을 받은 사용자 ID
    private String receiverName; // 요청을 받은 사용자 이름
    private MatchingStatus status; // 매칭 상태 (PENDING, ACCEPTED, REJECTED)
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 업데이트 시간
    private String message; // 요청 메시지 (선택적)

    public MatchingEntity(String matchingId, String senderId, String senderName, String receiverId, String receiverName, MatchingStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, String message) {
        this.matchingId = matchingId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }

    public String getMatchingId() {
        return matchingId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public MatchingStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(MatchingStatus status) {
        this.status = status;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
