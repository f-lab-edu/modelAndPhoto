package com.api.dto.conversation;

import com.api.dto.message.MessageDto;

import java.util.List;

public class ConversationResponse {

    private final String conversationId;
    private final List<MessageDto> messageDtos;

    public ConversationResponse(String conversationId, List<MessageDto> messageDtos) {
        this.conversationId = conversationId;
        this.messageDtos = messageDtos;
    }

    public String getConversationId() {
        return conversationId;
    }

    public List<MessageDto> getMessages() {
        return messageDtos;
    }
}
