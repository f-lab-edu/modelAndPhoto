package com.api.repository;

import com.api.entity.ConversationEntity;
import com.api.dto.message.MessageRequest;
import com.api.entity.MessageEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConversationRepository {

    private Map<String, ConversationEntity> conversationEntityMap = new HashMap<>();
    private Map<String, MessageEntity> messageEntityMap = new HashMap<>();

    public List<ConversationEntity> retrieve(String sessionUserId) {
        return conversationEntityMap.values()
                .stream()
                .filter(m -> m.getParticipantIds().contains(sessionUserId))
                .collect(Collectors.toList());
    }

    public String getConversationId(MessageRequest messageRequest) {

        List<String> participantIds = List.of(messageRequest.getSenderId(), messageRequest.getReceiverId());

        return conversationEntityMap.values()
                .stream()
                .filter(conversationEntity -> conversationEntity.getParticipantIds().containsAll(participantIds))  // 모든 참여자가 포함되어 있는지
                .map(ConversationEntity::getConversationId)
                .findAny().orElse(null);
    }

    public String createConversation(MessageRequest messageRequest) {

        String conversationId = "conv_123";  // todo

        conversationEntityMap.put(conversationId, new ConversationEntity(conversationId, List.of(messageRequest.getSenderId(), messageRequest.getReceiverId()), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now()));

        return conversationId;
    }
}
