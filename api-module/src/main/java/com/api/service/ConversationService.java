package com.api.service;

import com.api.dto.conversation.ConversationDto;
import com.api.repository.ConversationRepository;
import com.api.dto.message.MessageDto;
import com.api.entity.MessageEntity;
import com.api.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public ConversationService(ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public List<ConversationDto> retrieve(String sessionUserId) {
        return conversationRepository.retrieve(sessionUserId).stream()
                .map(conversationEntity -> new ConversationDto(
                        conversationEntity.getConversationId(),
                        conversationEntity.getParticipantIds(),
                        conversationEntity.getLastMessageTimestamp()))
                .collect(Collectors.toList());
    }

    public List<MessageDto> retrieveMessageInConversation(String conversationId, LocalDateTime startTime, LocalDateTime endTime) {

        List<MessageEntity> messageEntities = messageRepository.retrieveMessageInConversation(conversationId, startTime, endTime);

        return messageEntities.stream()
                .map(messageEntity -> new MessageDto(
                        messageEntity.getMessageId(),
                        messageEntity.getSenderId(),
                        messageEntity.getReceiverId(),
                        messageEntity.getMessageContent(),
                        messageEntity.getTimestamp(),
                        messageEntity.getMessageStatus()))
                .collect(Collectors.toList());
    }
}
