package com.api.conversation;

import com.api.message.Message;

import java.util.List;

public class ConversationResponse {

    private String conversationId;
    private List<Message> messages;

    public ConversationResponse(String conversationId, List<Message> messages) {
        this.conversationId = conversationId;
        this.messages = messages;
    }

    public String getConversationId() {
        return conversationId;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
