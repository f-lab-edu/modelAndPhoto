package com.api.service;

import com.api.dto.message.ConversationMessageStatusResponse;
import com.api.dto.message.MessageRequest;
import com.api.dto.message.MessageResponse;
import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import com.api.repository.MessageRepository;
import com.api.util.IdGenerator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageResponse send(MessageRequest messageRequest) {

        if (messageRequest.getFileId() != null && messageRequest.getMessage() != null) {
            throw new IllegalArgumentException("fildId와 message 필드가 동시에 존재할 수 없습니다.");
        } else if (messageRequest.getFileId() == null && messageRequest.getMessage() == null) {
            throw new IllegalArgumentException("fileId와 message 필드중 적어도 하나는 있어야 합니다.");
        }

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

        return new MessageResponse(savedMessageEntity.getMessageId(), savedMessageEntity.getConversationId(), savedMessageEntity.getSenderId(), savedMessageEntity.getReceiverId(), savedMessageEntity.getTimestamp(), savedMessageEntity.getMessageStatus());
    }

    public ConversationMessageStatusResponse updateStatusRead(String conversationId, String readerId) {

        messageRepository.updateMessageStatus(conversationId, readerId, MessageStatus.READ);

        MessageEntity findMessageEntity = messageRepository.findById(conversationId).orElseThrow(() -> new EntityNotFoundException("Message not found"));

        return new ConversationMessageStatusResponse(findMessageEntity.getConversationId(), findMessageEntity.getMessageStatus());
    }
}
