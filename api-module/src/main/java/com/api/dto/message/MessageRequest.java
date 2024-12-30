package com.api.dto.message;

import lombok.Getter;

@Getter
public class MessageRequest {

    private String senderId;
    private String receiverId;
    private String conversationId;
    private String message;
    private String fileId;

    public MessageRequest(String senderId, String receiverId, String conversationId, String message, String fileId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.conversationId = conversationId;
        this.message = message;
        this.fileId = fileId;
    }

}
