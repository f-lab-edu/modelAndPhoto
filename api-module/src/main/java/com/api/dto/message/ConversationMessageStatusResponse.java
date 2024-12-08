package com.api.dto.message;

import com.api.enums.MessageStatus;

public class ConversationMessageStatusResponse {

    private String conversationId;
    private MessageStatus messageStatus;

    public ConversationMessageStatusResponse(String conversationId, MessageStatus messageStatus) {
        this.conversationId = conversationId;
        this.messageStatus = messageStatus;
    }

    public String getConversationId() {
        return conversationId;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }
}
