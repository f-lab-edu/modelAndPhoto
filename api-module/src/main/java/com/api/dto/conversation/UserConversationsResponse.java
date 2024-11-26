package com.api.dto.conversation;

import java.util.List;

public class UserConversationsResponse {

    private final List<ConversationDto> conversationDtos;

    public UserConversationsResponse(List<ConversationDto> conversationDtos) {
        this.conversationDtos = conversationDtos;
    }

    public List<ConversationDto> getConversations() {
        return conversationDtos;
    }
}
