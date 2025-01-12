package com.api.controller;

import com.api.dto.message.*;
import com.api.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    //메시지 전송(파일 전송 가능)
    @PostMapping
    public CompletableFuture<ResponseEntity<MessageResponse>> sendMessage(@RequestBody @Valid MessageRequestDto request) {

        MessageRequest messageRequest = new MessageRequest(
                request.getSenderId(),
                request.getReceiverId(),
                request.getConversationId(),
                request.getMessage(),
                request.getFileId());
        return messageService.send(messageRequest)
                .thenApply(ResponseEntity::ok);
    }

    // 대화방 메시지 읽음 상태 업데이트
    @PostMapping("/conversations/{conversation_id}/read")
    public ResponseEntity<ReadStatusUpdateResponse> updateReadStatus(
            @PathVariable("conversation_id") String conversationId
            , @RequestBody ReadStatusUpdateRequest request) throws ExecutionException, InterruptedException {

        CompletableFuture<ConversationMessageStatusResponse> conversationMessageStatusResponseCompletableFuture = messageService.updateStatusRead(conversationId, request.getReaderId());

        return ResponseEntity.ok(new ReadStatusUpdateResponse(conversationMessageStatusResponseCompletableFuture.get(), LocalDateTime.now()));
    }
}
