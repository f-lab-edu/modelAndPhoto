package com.api.message.dto;

import javax.validation.constraints.NotBlank;

public class MessageRequestDto {

    @NotBlank(message = "발신인 id는 필수값입니다.")
    public String senderId;

    @NotBlank(message = "수신인 id는 필수값입니다.")
    public String receiverId;

    public String message;

    public String fileId;

    public MessageRequestDto(String senderId, String receiverId, String message, String fileId) {
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
