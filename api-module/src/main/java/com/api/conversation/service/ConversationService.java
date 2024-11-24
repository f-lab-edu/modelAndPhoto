package com.api.conversation.service;

import com.api.conversation.dto.ConversationDto;
import com.api.conversation.entity.ConversationEntity;
import com.api.conversation.repository.ConversationRepository;
import com.api.message.dto.MessageDto;
import com.api.message.entity.MessageEntity;
import com.api.message.repository.MessageRepository;
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

        List<ConversationEntity> conversationEntities = conversationRepository.retrieve(sessionUserId);

        return conversationEntities.stream()
                .map(conversationEntity -> new ConversationDto(
                        conversationEntity.getConversationId()
                        , conversationEntity.getParticipantIds()
                        , conversationEntity.getLastMessageTimestamp()))
                .collect(Collectors.toList());
    }

    public List<MessageDto> retrieveMessageInConversation(String conversationId, LocalDateTime startTime, LocalDateTime endTime) {

        List<MessageEntity> messageEntities = messageRepository.retrieveMessageInConversation(conversationId, startTime, endTime);

        return messageEntities.stream()
                .map(messageEntity -> new MessageDto(
                          messageEntity.getMessageId()
                        , messageEntity.getSenderId()
                        , messageEntity.getReceiverId()
                        , messageEntity.getMessageContent()
                        , messageEntity.getTimestamp()
                        , messageEntity.getMessageStatus()))
                .collect(Collectors.toList());
    }
}
