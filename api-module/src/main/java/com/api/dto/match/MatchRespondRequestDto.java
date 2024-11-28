package com.api.dto.match;

import com.api.enums.MatchingStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MatchRespondRequestDto {

    @NotBlank(message = "매칭요청Id는 필수값입니다.")
    @Size(max = 50)
    private String requestId;

    @NotNull(message = "응답 값은 필수입니다.")
    private MatchingStatus response; // accepted or rejected

    public MatchRespondRequestDto(String requestId, MatchingStatus response) {
        this.requestId = requestId;
        this.response = response;
    }

    public String getRequestId() {
        return requestId;
    }

    public MatchingStatus getResponse() {
        return response;
    }
}
