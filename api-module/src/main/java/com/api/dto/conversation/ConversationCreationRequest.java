package com.api.dto.conversation;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class ConversationCreationRequest {

    @NotNull(message = "users는 null일 수 없습니다.")
    @Size(min = 2, message = "대화방은 최소 2명의 사용자가 필요합니다.")
    private final List<String> users;

    public ConversationCreationRequest(List<String> users) {
        this.users = users;
    }
}
