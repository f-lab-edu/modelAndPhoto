package com.api.message.entity;

import com.api.message.enums.MessageStatus;

import java.time.LocalDateTime;

public class MessageEntity {

    public String messageId;
    public String conversationId;
    public String senderId;
    public String receiverId;
    public String fileId;
    public String messageContent;
    public LocalDateTime timestamp;
    public MessageStatus messageStatus;

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
