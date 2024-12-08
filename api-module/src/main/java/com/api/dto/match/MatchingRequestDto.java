package com.api.dto.match;

import javax.validation.constraints.NotBlank;

public class MatchingRequestDto {

    @NotBlank(message = "요청자Id는 필수값입니다.")
    private String senderId;

    @NotBlank(message = "수신자Id는 필수값입니다.")
    private String receiverId;

    @NotBlank(message = "요청 내용은 필수값입니다.")
    private String message;

    public MatchingRequestDto(String senderId, String receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
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
}
