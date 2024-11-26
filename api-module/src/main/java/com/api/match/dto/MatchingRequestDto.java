package com.api.match.dto;

import javax.validation.constraints.NotBlank;

public class MatchingRequestDto {

    @NotBlank(message = "요청자Id는 필수값입니다.")
    private String senderId;

    @NotBlank(message = "수신자Id는 필수값입니다.")
    private String receiverId;

    public MatchingRequestDto(String senderId, String receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}
