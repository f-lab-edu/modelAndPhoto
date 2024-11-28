package com.api.dto.conversation;

import java.time.LocalDateTime;
import java.util.List;

public class ConversationDto {
    private String conversationId;
    private List<String> participantIds;
    private LocalDateTime lastMessageTimestamp;

    public ConversationDto(String conversationId, List<String> participantIds, LocalDateTime lastMessageTimestamp) {
        this.conversationId = conversationId;
        this.participantIds = participantIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
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
}
