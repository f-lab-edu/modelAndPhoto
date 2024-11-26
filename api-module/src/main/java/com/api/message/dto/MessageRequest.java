package com.api.message.dto;


public class MessageRequest {

    private String senderId;
    private String receiverId;
    private String message;
    private String fileId;

    public MessageRequest(String senderId, String receiverId, String message, String fileId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.fileId = fileId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public String getFileId() {
        return fileId;
    }
}
