package com.api.controller;

import com.api.dto.message.*;
import com.api.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    //메시지 전송(파일 전송 가능)
    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody @Valid MessageRequestDto request) {

        return ResponseEntity.ok(messageService.send(new MessageRequest(
                                                        request.getSenderId(),
                                                        request.getReceiverId(),
                                                        request.getMessage(),
                                                        request.getFileId())));
    }

    // 대화방 메시지 읽음 상태 업데이트
    @PostMapping("/conversations/{conversation_id}/read")
    public ResponseEntity<ReadStatusUpdateResponse> updateReadStatus(
            @PathVariable("conversation_id") String conversationId
            , @RequestBody ReadStatusUpdateRequest request) {

        ConversationMessageStatusResponse conversationMessageStatusResponse = messageService.updateStatusRead(conversationId, request.getReaderId());

        return ResponseEntity.ok(new ReadStatusUpdateResponse(conversationMessageStatusResponse.getConversationId(),
                                                            conversationMessageStatusResponse.getMessageStatus(),
                                                            LocalDateTime.now()));
    }
}
