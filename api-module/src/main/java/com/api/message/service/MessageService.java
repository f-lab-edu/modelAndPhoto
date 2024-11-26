package com.api.message.service;

import com.api.conversation.repository.ConversationRepository;
import com.api.message.dto.MessageRequest;
import com.api.message.dto.MessageResponse;
import com.api.message.entity.MessageEntity;
import com.api.message.enums.MessageStatus;
import com.api.message.repository.MessageRepository;
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

        /**
         * todo
         * 1. 대화방이 아직 생성되지 않았다면 대화방 생성 후 메시지 생성!
         */

        String conversationId = conversationRepository.getConversationId(messageRequest);

        if (conversationId == null) {
            conversationId = conversationRepository.createConversation(messageRequest);
        }

        String messageId = "msg_001";  // todo

        MessageEntity messageEntity = messageRepository.createMessage(new MessageEntity(
                messageId,
                conversationId,
                messageRequest.getSenderId(),
                messageRequest.getReceiverId(),
                messageRequest.getMessage(),
                messageRequest.getFileId(),
                LocalDateTime.now(),
                MessageStatus.SENT));

        return new MessageResponse(messageEntity.getMessageId(), messageEntity.getConversationId(), messageEntity.getSenderId(), messageEntity.getReceiverId(), messageEntity.getTimestamp(), messageEntity.getMessageStatus());
    }

    public void updateStatusRead(String conversationId, String readerId) {
        messageRepository.updateStatusRead(conversationId, readerId);
    }
}
