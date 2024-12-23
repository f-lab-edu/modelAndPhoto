package com.api.service;

import com.api.dto.conversation.ConversationDto;
import com.api.dto.message.MessageDto;
import com.api.entity.ConversationEntity;
import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import com.api.repository.ConversationRepository;
import com.api.repository.MessageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConversationServiceTest {

    @InjectMocks
    private ConversationService conversationService;

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private MessageRepository messageRepository;

    @Test
    @DisplayName("대화방 조회 시 ConversationDto의 participantIds 중 sessionId와 일치하는 id가 존재해야 한다.")
    void testRetrieve() {
        //given
        String sessionUserId = "user_123";
        doReturn(conversationEntities()).when(conversationRepository).retrieve(sessionUserId);

        //when
        List<ConversationDto> retrieveConversation = conversationService.retrieve(sessionUserId);

        //then
        assertThat(retrieveConversation).isNotNull();

        if (retrieveConversation != null) {
            for (ConversationDto conversationDto : retrieveConversation) {
                assertThat(conversationDto.getParticipantIds()).contains(sessionUserId);
            }
        }

        // verify
        verify(conversationRepository, times(1)).retrieve(sessionUserId);
    }

    @Test
    @DisplayName("대화방 내 메시지 조회 시 startTime과 endTime의 범위를 벗어나지 않는다.")
    void testRetrieveMessageInConversation() {
        //given
        String conversationId = "conv_123";
        LocalDateTime startTime = LocalDateTime.now().minusDays(7);
        LocalDateTime endTime = LocalDateTime.now();

        doReturn(messageEntitiesResponse()).when(messageRepository).findByConversationIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(any(String.class), any(LocalDateTime.class), any(LocalDateTime.class));

        //when
        List<MessageDto> messageDtos = conversationService.retrieveMessageInConversation(conversationId, startTime, endTime);

        //then
        assertThat(messageDtos).isNotNull();

        if (messageDtos != null) {
            for (MessageDto messageDto : messageDtos) {
                assertThat(messageDto.getTimestamp()).isBefore(endTime).isAfter(startTime);
            }
        }

        // verify
        verify(messageRepository, times(1)).findByConversationIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(any(String.class), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    // 대화방 엔티티 목록
    private List<ConversationEntity> conversationEntities() {
        return List.of(new ConversationEntity("conv_123", List.of("user_123", "user_457"), LocalDateTime.now(), LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1)),
                new ConversationEntity("conv_124", List.of("user_123", "user_458"), LocalDateTime.now(), LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1)),
                new ConversationEntity("conv_125", List.of("user_123", "user_459"), LocalDateTime.now(), LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1)),
                new ConversationEntity("conv_126", List.of("user_123", "user_451"), LocalDateTime.now(), LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1)),
                new ConversationEntity("conv_127", List.of("user_123", "user_452"), LocalDateTime.now(), LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1)));
    }

    // 메시지 엔티티 목록
    private List<MessageEntity> messageEntitiesResponse() {
        return List.of(new MessageEntity("msg_001", "conv_001", "user_123", "user_456", "", "안", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_002", "conv_001", "user_123", "user_456", "", "녕", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_003", "conv_001", "user_123", "user_456", "", "하", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_004", "conv_001", "user_123", "user_456", "", "세", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_005", "conv_001", "user_123", "user_456", "", "요", LocalDateTime.now().minusDays(1), MessageStatus.READ));
    }

}