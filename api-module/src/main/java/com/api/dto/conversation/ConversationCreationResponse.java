package com.api.dto.conversation;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConversationCreationResponse {

    private final String conversationId;
    private final LocalDateTime createdAt; // 생성 시간

    public ConversationCreationResponse(String conversationId, LocalDateTime createdAt) {
        this.conversationId = conversationId;
        this.createdAt = createdAt;
    }
}
