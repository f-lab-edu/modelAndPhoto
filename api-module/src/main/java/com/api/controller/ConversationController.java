package com.api.controller;

import com.api.dto.conversation.ConversationDto;
import com.api.dto.conversation.ConversationResponse;
import com.api.dto.conversation.UserConversationsResponse;
import com.api.service.ConversationService;
import com.api.dto.message.MessageDto;
import org.springframework.format.annotation.DateTimeFormat;
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

        return ResponseEntity.ok(new UserConversationsResponse(conversations));
    }

    // 대화 내 메시지 조회 (시간별 range 조회)
    @GetMapping(value = "/{conversation_id}")
    public ResponseEntity<ConversationResponse> getMessagesInConversation(
            @PathVariable("conversation_id") String conversationId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {

        List<MessageDto> messageDtos = conversationService.retrieveMessageInConversation(conversationId, startTime, endTime);

        return ResponseEntity.ok(new ConversationResponse(conversationId, messageDtos));
    }
}
