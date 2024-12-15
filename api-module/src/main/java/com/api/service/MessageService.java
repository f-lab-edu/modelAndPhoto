package com.api.service;

import com.api.dto.message.ConversationMessageStatusResponse;
import com.api.dto.message.MessageRequest;
import com.api.dto.message.MessageResponse;
import com.api.entity.ConversationEntity;
import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import com.api.repository.ConversationRepository;
import com.api.repository.MessageRepository;
import com.api.util.IdGenerator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    public MessageResponse send(MessageRequest messageRequest) {

        if (messageRequest.getFileId() != null && messageRequest.getMessage() != null) {
            throw new IllegalArgumentException("fildId와 message 필드가 동시에 존재할 수 없습니다.");
        }else if(messageRequest.getFileId() == null && messageRequest.getMessage() == null){
            throw new IllegalArgumentException("fileId와 message 필드중 적어도 하나는 있어야 합니다.");
        }

        String conversationId = conversationRepository.getConversationId(messageRequest.getSenderId(), messageRequest.getReceiverId()).orElse(null);
        String generatedMessageId = IdGenerator.getGenerateMessageId();

        if (conversationId == null) {
            ConversationEntity saved = conversationRepository.save(new ConversationEntity(IdGenerator.getGenerateConversationId(), List.of(messageRequest.getSenderId(), messageRequest.getReceiverId()), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()));
            conversationId = saved.getConversationId();
        }

        MessageEntity savedMessageEntity = messageRepository.save(new MessageEntity(
                generatedMessageId,
                conversationId,
                messageRequest.getSenderId(),
                messageRequest.getReceiverId(),
                messageRequest.getMessage(),
                messageRequest.getFileId(),
                LocalDateTime.now(),
                MessageStatus.SENT));

        return new MessageResponse(savedMessageEntity.getMessageId(), savedMessageEntity.getConversationId(), savedMessageEntity.getSenderId(), savedMessageEntity.getReceiverId(), savedMessageEntity.getTimestamp(), savedMessageEntity.getMessageStatus());
    }

    @Transactional
    public ConversationMessageStatusResponse updateStatusRead(String conversationId, String readerId) {

        messageRepository.updateMessageStatus(conversationId, readerId, MessageStatus.READ);

        MessageEntity findMessageEntity = messageRepository.findById(conversationId).orElseThrow(() -> new EntityNotFoundException("Message not found"));

        return new ConversationMessageStatusResponse(findMessageEntity.getConversationId(), findMessageEntity.getMessageStatus());
    }
}
