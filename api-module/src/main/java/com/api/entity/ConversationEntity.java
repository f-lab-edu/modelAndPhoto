package com.api.entity;

import java.time.LocalDateTime;
import java.util.List;

public class ConversationEntity {

    public String conversationId; // Primary Key

    public List<String> participantIds; // 대화 참여자 ID 리스트

    public LocalDateTime lastMessageTimestamp; // 마지막 메시지 시간

    public LocalDateTime createdAt; // 생성 시간

    public LocalDateTime updatedAt; // 업데이트 시간

    public ConversationEntity(String conversationId, List<String> participantIds, LocalDateTime lastMessageTimestamp, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.conversationId = conversationId;
        this.participantIds = participantIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getConversationId() {
        return conversationId;
    }

    public List<String> getParticipantIds() {
        return participantIds;
    }

    public LocalDateTime getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
