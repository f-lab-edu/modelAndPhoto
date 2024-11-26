package com.api.conversation;

import com.api.conversation.dto.ConversationDto;
import com.api.conversation.dto.ConversationResponse;
import com.api.conversation.dto.UserConversationsResponse;
import com.api.conversation.service.ConversationService;
import com.api.message.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    private final String SESSION_USER_ID = "user_123";  // sessionUserId

    // 사용자 간 대화 목록 조회
    @GetMapping
    public ResponseEntity<UserConversationsResponse> getUserConversations() {

        List<ConversationDto> conversations = conversationService.retrieve(SESSION_USER_ID);

        UserConversationsResponse response = new UserConversationsResponse(conversations);
        return ResponseEntity.ok(response);
    }

    // 대화 내 메시지 조회 (시간별 range 조회)
    @GetMapping(value = "/{conversation_id}", produces = "application/json")
    public ResponseEntity<ConversationResponse> getMessagesInConversation(
            @PathVariable("conversation_id") String conversationId,
            @RequestParam("start") LocalDateTime startTime,
            @RequestParam("end") LocalDateTime endTime) {

        List<MessageDto> messageDtos = conversationService.retrieveMessageInConversation(conversationId, startTime, endTime);

        return ResponseEntity.ok(new ConversationResponse(conversationId, messageDtos));
    }
}
