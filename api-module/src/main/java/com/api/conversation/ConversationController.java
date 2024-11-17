package com.api.conversation;

import com.api.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages/conversations")
public class ConversationController {

    // 사용자 간 대화 목록 조회
    @GetMapping
    public ResponseEntity<UserConversationsResponse> getUserConversations() {
        List<Conversation> conversations = List.of(
                new Conversation("conv_123", List.of("user_123", "user_456"), LocalDateTime.now()),
                new Conversation("conv_124", List.of("user_123", "user_789"), LocalDateTime.now())
        );
        UserConversationsResponse response = new UserConversationsResponse(conversations);
        return ResponseEntity.ok(response);
    }

    // 대화 내 메시지 조회 (시간별 range 조회)
    @GetMapping(value = "/{conversation_id}", produces = "application/json")
    public ResponseEntity<ConversationResponse> getMessagesInConversation(
            @PathVariable("conversation_id") String conversationId,
            @RequestParam("start") String startTime,
            @RequestParam("end") String endTime) {

        List<Message> messages = List.of(
                new Message("msg_789", "user_123", "user_456", "안녕하세요!", LocalDateTime.parse("2024-11-01T14:30:00"), true),
                new Message("msg_790", "user_456", "user_123", "반갑습니다!", LocalDateTime.parse("2024-11-02T14:30:00"), false)
        );
        ConversationResponse response = new ConversationResponse(conversationId, messages);
        return ResponseEntity.ok(response);
    }
}
