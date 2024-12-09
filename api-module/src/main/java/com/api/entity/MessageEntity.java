package com.api.entity;

import com.api.enums.MessageStatus;

import java.time.LocalDateTime;

public class MessageEntity {

    private String messageId;
    private String conversationId;
    private String senderId;
    private String receiverId;
    private String fileId;
    private String messageContent;
    private LocalDateTime timestamp;
    private MessageStatus messageStatus;

    public MessageEntity(String messageId, String conversationId, String senderId, String receiverId, String fileId, String messageContent, LocalDateTime timestamp, MessageStatus messageStatus) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.fileId = fileId;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
        this.messageStatus = messageStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getFileId() {
        return fileId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}
