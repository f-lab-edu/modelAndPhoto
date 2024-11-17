package com.api.message;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    //메시지 전송(파일 전송 가능)
    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
        return ResponseEntity
                .ok(new MessageResponse("user_123"
                        , "user_456"
                        , "안녕하세요!"
                        , ""
                        , LocalDateTime.now()
                        , MessageStatus.SENT));
    }


    // 대화방 메시지 읽음 상태 업데이트
    @PostMapping("/conversations/{conversation_id}/read")
    public ResponseEntity<ReadStatusUpdateResponse> updateReadStatus(
            @PathVariable("conversation_id") String conversationId
            , @RequestBody ReadStatusUpdateRequest request) {
        ReadStatusUpdateResponse response = new ReadStatusUpdateResponse(conversationId, MessageStatus.READ, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
