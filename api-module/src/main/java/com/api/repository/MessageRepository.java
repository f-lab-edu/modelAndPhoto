package com.api.repository;

import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MessageRepository {

    Map<String, MessageEntity> messageEntityMap = new HashMap<>();

    public MessageEntity createMessage(MessageEntity messageEntity) {
        return messageEntityMap.put(messageEntity.getMessageId(), messageEntity);
    }

    public void updateStatusRead(String conversationId, String readerId) {

        messageEntityMap.values().stream()
                .filter(entity -> entity.getConversationId().equals(conversationId) && entity.getReceiverId().equals(readerId))
                .forEach(entity -> {
                    entity.setMessageStatus(MessageStatus.READ);
                    messageEntityMap.put(entity.getMessageId(), entity);
                });
    }

    public List<MessageEntity> retrieveMessageInConversation(String conversationId, LocalDateTime startTime, LocalDateTime endTime) {

        return messageEntityMap.values()
                .stream()
                .filter(m -> m.getConversationId().equals(conversationId))
                .filter(m -> !m.getTimestamp().isBefore(startTime) && !m.getTimestamp().isAfter(endTime))
                .collect(Collectors.toList());
    }
}
