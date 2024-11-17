package com.api.conversation;

import java.time.LocalDateTime;
import java.util.List;

public class Conversation {
    private String conversationId;
    private List<String> participantIds;
    private LocalDateTime lastMessageTimestamp;

    public Conversation(String conversationId, List<String> participantIds, LocalDateTime lastMessageTimestamp) {
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
