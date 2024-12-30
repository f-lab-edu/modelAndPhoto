package com.api.dto.message;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MessageRequestDto {

    @NotBlank(message = "발신인 id는 필수값입니다.")
    public String senderId;

    @NotBlank(message = "수신인 id는 필수값입니다.")
    public String receiverId;

    public String conversationId;

    public String message;

    public String fileId;

    public MessageRequestDto(String senderId, String receiverId, String conversationId, String message, String fileId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.conversationId = conversationId;
        this.message = message;
        this.fileId = fileId;
    }
}
