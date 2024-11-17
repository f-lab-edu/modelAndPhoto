package com.api.conversation;

import java.util.List;

public class UserConversationsResponse {

    private List<Conversation> conversations;

    public UserConversationsResponse(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }
}
