package com.api.service;

import com.api.dto.message.ConversationMessageStatusResponse;
import com.api.dto.message.MessageRequest;
import com.api.dto.message.MessageResponse;
import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import com.api.repository.MessageRepository;
import com.api.util.IdGenerator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Async("taskExecutor")
    public CompletableFuture<MessageResponse> send(MessageRequest messageRequest) {
        if (messageRequest.getFileId() != null && messageRequest.getMessage() != null) {
            return CompletableFuture.failedFuture(
                    new IllegalArgumentException("fileId와 message 필드는 동시에 존재할 수 없습니다.")
            );
        } else if (messageRequest.getFileId() == null && messageRequest.getMessage() == null) {
            return CompletableFuture.failedFuture(
                    new IllegalArgumentException("fileId와 message 필드 중 적어도 하나는 있어야 합니다.")
            );
        }

        try {
            String generatedMessageId = IdGenerator.getGenerateMessageId();

            MessageEntity savedMessageEntity = messageRepository.save(new MessageEntity(
                    generatedMessageId,
                    messageRequest.getConversationId(),
                    messageRequest.getSenderId(),
                    messageRequest.getReceiverId(),
                    messageRequest.getMessage(),
                    messageRequest.getFileId(),
                    LocalDateTime.now(),
                    MessageStatus.SENT));

            MessageResponse response = new MessageResponse(
                    savedMessageEntity.getMessageId(),
                    savedMessageEntity.getConversationId(),
                    savedMessageEntity.getSenderId(),
                    savedMessageEntity.getReceiverId(),
                    savedMessageEntity.getTimestamp(),
                    savedMessageEntity.getMessageStatus());

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<ConversationMessageStatusResponse> updateStatusRead(String conversationId, String readerId) {
        try {
            // 상태 업데이트
            messageRepository.updateMessageStatus(conversationId, readerId, MessageStatus.READ);

            // 메시지 조회
            MessageEntity findMessageEntity = messageRepository.findById(conversationId)
                    .orElseThrow(() -> new EntityNotFoundException("Message not found"));

            // 응답 생성
            ConversationMessageStatusResponse response = new ConversationMessageStatusResponse(
                    findMessageEntity.getConversationId(),
                    findMessageEntity.getMessageStatus()
            );

            // 성공적으로 완료
            return CompletableFuture.completedFuture(response);
        } catch (EntityNotFoundException ex) {
            // 특정 예외는 그대로 전달
            return CompletableFuture.failedFuture(ex);
        } catch (Exception ex) {
            // 기타 예외는 래핑하여 전달
            return CompletableFuture.failedFuture(new RuntimeException("An unexpected error occurred", ex));
        }
    }
}
