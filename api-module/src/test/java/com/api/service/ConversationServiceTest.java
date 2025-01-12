package com.api.service;

import com.api.dto.conversation.ConversationCreationResponse;
import com.api.dto.conversation.ConversationDto;
import com.api.dto.message.MessageDto;
import com.api.entity.ConversationEntity;
import com.api.entity.ConversationParticipantEntity;
import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import com.api.repository.ConversationRepository;
import com.api.repository.MessageRepository;
import com.api.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
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
        doReturn(conversationEntities()).when(conversationRepository).findDistinctByParticipants(sessionUserId);

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
        verify(conversationRepository, times(1)).findDistinctByParticipants(sessionUserId);
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

    @Test
    @DisplayName("대화방 생성 성공")
    void createConversation_Success() {
        // Given
        String user1 = "PHO_001";
        String user2 = "MOD_001";
        List<String> userIds = List.of(user1, user2);
        String conversationId = "CON_001";
        LocalDateTime now = LocalDateTime.now();

        ConversationEntity expectedConversation = ConversationEntity.builder()
                .conversationId(conversationId)
                .createdAt(now)
                .lastMessageTimestamp(now)
                .participants(Arrays.asList(
                        ConversationParticipantEntity.builder()
                                .userId(user1)
                                .conversationId(conversationId)
                                .build(),
                        ConversationParticipantEntity.builder()
                                .userId(user2)
                                .conversationId(conversationId)
                                .build()
                ))
                .build();

        // When
        when(conversationRepository.save(any(ConversationEntity.class))).thenReturn(expectedConversation);

        ConversationCreationResponse response = conversationService.createConversation(userIds);

        // Then
        assertThat(response).isNotNull();
        assertThat(conversationId).isEqualTo(response.getConversationId());
        assertThat(response.getCreatedAt()).isNotNull();

        verify(conversationRepository, times(1)).save(any(ConversationEntity.class));
    }

    @Test
    @DisplayName("대화방 생성 실패 - 사용자 목록이 null인 경우")
    void createConversation_Fail_NullUserIds() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            conversationService.createConversation(null);
        });

        verify(conversationRepository, never()).save(any(ConversationEntity.class));
    }

    // 대화방 엔티티 목록
    private List<ConversationEntity> conversationEntities() {
        return List.of(
                createConversation("conv_123", List.of("user_123", "user_457")),
                createConversation("conv_124", List.of("user_123", "user_458")),
                createConversation("conv_125", List.of("user_123", "user_459")),
                createConversation("conv_126", List.of("user_123", "user_451")),
                createConversation("conv_127", List.of("user_123", "user_452"))
        );
    }

    // 메시지 엔티티 목록
    private List<MessageEntity> messageEntitiesResponse() {
        return List.of(new MessageEntity("msg_001", "conv_001", "user_123", "user_456", "", "안", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_002", "conv_001", "user_123", "user_456", "", "녕", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_003", "conv_001", "user_123", "user_456", "", "하", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_004", "conv_001", "user_123", "user_456", "", "세", LocalDateTime.now().minusDays(1), MessageStatus.READ),
                new MessageEntity("msg_005", "conv_001", "user_123", "user_456", "", "요", LocalDateTime.now().minusDays(1), MessageStatus.READ));
    }

    private static ConversationEntity createConversation(String conversationId, List<String> userIds) {
        // 1. 참여자 리스트 생성
        List<ConversationParticipantEntity> participants = new ArrayList<>();
        if (userIds != null) {
            for (String userId : userIds) {
                participants.add(
                        ConversationParticipantEntity.builder()
                                .userId(userId)
                                .build()
                );
            }
        }
        // 2. 대화방 생성
        ConversationEntity conversation = ConversationEntity.builder()
                .conversationId(conversationId)
                .createdAt(LocalDateTime.now())
                .lastMessageTimestamp(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .participants(participants) // 참여자 리스트 설정
                .build();
        // 3. 부모-자식 관계 설정
        for (ConversationParticipantEntity participant : participants) {
            participant.setConversationId(conversation.getConversationId());
        }
        return conversation;
    }

}