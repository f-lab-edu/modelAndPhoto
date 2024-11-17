package com.api.message;

public class MessageRequest {

    public String senderId;
    public String receiverId;
    public String message;
    public String fileId;

    public MessageRequest(String senderId, String receiverId, String message, String fileId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.fileId = fileId;
    }
}
