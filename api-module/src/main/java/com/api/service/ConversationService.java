package com.api.service;

import com.api.dto.conversation.ConversationCreationResponse;
import com.api.dto.conversation.ConversationDto;
import com.api.dto.message.MessageDto;
import com.api.entity.ConversationEntity;
import com.api.entity.ConversationParticipantEntity;
import com.api.entity.MessageEntity;
import com.api.repository.ConversationRepository;
import com.api.repository.MessageRepository;
import com.api.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return conversationRepository.findDistinctByParticipants(sessionUserId).stream()
                .map(conversationEntity -> new ConversationDto(
                        conversationEntity.getConversationId(),
                        conversationEntity.getParticipants().stream().map(ConversationParticipantEntity::getUserId).collect(Collectors.toList()),
                        conversationEntity.getLastMessageTimestamp()))
                .collect(Collectors.toList());
    }

    public List<MessageDto> retrieveMessageInConversation(String conversationId, LocalDateTime startTime, LocalDateTime endTime) {

        List<MessageEntity> messageEntities = messageRepository.findByConversationIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(conversationId, startTime, endTime);

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

    /**
     * 새로운 대화방 생성 및 저장
     */
    public ConversationCreationResponse createConversation(List<String> userIds) {

        String conversationId = IdGenerator.getGenerateConversationId();

        // 1. 참여자 리스트 생성
        List<ConversationParticipantEntity> participants = new ArrayList<>();
        for (String userId : userIds) {
            participants.add(
                    ConversationParticipantEntity.builder()
                            .userId(userId)
                            .build()
            );
        }
        // 2. 대화방 생성
        ConversationEntity conversation = ConversationEntity.builder()
                .conversationId(conversationId)
                .createdAt(LocalDateTime.now())
                .lastMessageTimestamp(LocalDateTime.now())
                .participants(participants) // 참여자 리스트 설정
                .build();
        // 3. 부모-자식 관계 설정 (참여자에 부모 설정)
        for (ConversationParticipantEntity participant : participants) {
            participant.setConversationId(conversation.getConversationId());
        }
        // 4. 저장 및 반환 (CascadeType.ALL로 인해 자식도 자동 저장됨)
        ConversationEntity conversationEntity = conversationRepository.save(conversation);

        return new ConversationCreationResponse(conversationEntity.getConversationId(), conversationEntity.getCreatedAt());
    }
}
