package com.api.service;

import com.api.dto.message.ConversationMessageStatusResponse;
import com.api.repository.ConversationRepository;
import com.api.dto.message.MessageRequest;
import com.api.dto.message.MessageResponse;
import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import com.api.repository.MessageRepository;
import com.api.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        String conversationId = conversationRepository.getConversationId(messageRequest);
        String generatedMessageId = IdGenerator.generateId(IdGenerator.getPrefixMessage());

        if (conversationId == null) {
            conversationId = conversationRepository.createConversation(generatedMessageId, messageRequest);
        }

        MessageEntity messageEntity = messageRepository.createMessage(new MessageEntity(
                generatedMessageId,
                conversationId,
                messageRequest.getSenderId(),
                messageRequest.getReceiverId(),
                messageRequest.getMessage(),
                messageRequest.getFileId(),
                LocalDateTime.now(),
                MessageStatus.SENT));

        return new MessageResponse(messageEntity.getMessageId(), messageEntity.getConversationId(), messageEntity.getSenderId(), messageEntity.getReceiverId(), messageEntity.getTimestamp(), messageEntity.getMessageStatus());
    }

    public ConversationMessageStatusResponse updateStatusRead(String conversationId, String readerId) {

        MessageEntity messageEntity = messageRepository.updateStatusRead(conversationId, readerId);

        return new ConversationMessageStatusResponse(messageEntity.getConversationId(), messageEntity.getMessageStatus());
    }
}
