package com.api.service;

import com.api.dto.message.ConversationMessageStatusResponse;
import com.api.dto.message.MessageRequest;
import com.api.dto.message.MessageResponse;
import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import com.api.repository.MessageRepository;
import com.api.util.IdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    @DisplayName("메시지 전송 시 response에 conversationId가 생성된다.")
    void test_send() throws ExecutionException, InterruptedException {
        // given
        String generatedConversationID = IdGenerator.getGenerateConversationId();
        MessageRequest messageRequest = new MessageRequest("MOD_123", "PHO_456", generatedConversationID, "메시지내용", null);

        doReturn(new MessageEntity("MSG_001", generatedConversationID, "MOD_123", "PHO_456", null, "메시지내용", LocalDateTime.now(), MessageStatus.SENT))
                .when(messageRepository).save(any(MessageEntity.class));

        // when
        CompletableFuture<MessageResponse> messageResponse = messageService.send(messageRequest);

        // then
        assertThat(messageResponse.get().getStatus()).isEqualTo(MessageStatus.SENT);
        assertThat(messageResponse.get().getConversationId()).isEqualTo(generatedConversationID);

        // verify
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }

    @Test
    @DisplayName("메시지 읽음 처리")
    void test_update_status_read() throws ExecutionException, InterruptedException {
        // given
        String conversationId = "CON_123";
        String readerId = "PHO_456";

        doReturn(Optional.of(new MessageEntity("MSG_001", conversationId, "MOD_123", readerId, null, "메시지내용", LocalDateTime.now(), MessageStatus.READ))).when(messageRepository).findById(any(String.class));

        // when
        CompletableFuture<ConversationMessageStatusResponse> conversationMessageStatusResponseCompletableFuture = messageService.updateStatusRead(conversationId, readerId);

        // then
        assertThat(conversationMessageStatusResponseCompletableFuture.get().getConversationId()).isEqualTo(conversationId);
        assertThat(conversationMessageStatusResponseCompletableFuture.get().getMessageStatus()).isEqualTo(MessageStatus.READ);

        // verify
        verify(messageRepository, times(1)).updateMessageStatus(any(String.class), any(String.class), any(MessageStatus.class));
    }

    @Test
    @DisplayName("메시지 송신을 비동기로 처리")
    void testSendAsyncSuccess() throws ExecutionException, InterruptedException {
        String generatedConversationID = IdGenerator.getGenerateConversationId();
        MessageEntity savedMessageEntity = new MessageEntity("MSG_001", generatedConversationID, "MOD_123", "PHO_456", null, "메시지내용", LocalDateTime.now(), MessageStatus.SENT);
        when(messageRepository.save(any(MessageEntity.class))).thenReturn(savedMessageEntity);

        // 비동기 메서드 호출
        CompletableFuture<MessageResponse> futureResponse = messageService.send(new MessageRequest("MOD_123", "PHO_456", "CON_001", "메시지내용", null));

        // CompletableFuture가 완료될 때까지 기다림
        MessageResponse response = futureResponse.get();

        // 응답 검증
        assertThat(response).isNotNull();
        assertThat(savedMessageEntity.getMessageId()).isEqualTo(response.getMessageId());

        // messageRepository.save()가 정확히 한 번 호출되었는지 검증
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }

    @Test
    @DisplayName("메시지 messageRequest에 fileId와 message 필드가 동시에 존재하면 IllegalArgumentException을 던진다.")
    public void testSendAsyncWithBothFileIdAndMessage() {
        // 메시지와 파일 ID가 동시에 있는 요청
        MessageRequest invalidRequest = new MessageRequest("MOD_001", "PHO_001", "CON_001", "messageContent", "FIL_001");

        // 비동기 메서드 호출
        CompletableFuture<MessageResponse> futureResponse = messageService.send(invalidRequest);

        // 예외가 발생해야 하므로, CompletableFuture가 예외를 담고 있는지 확인
        ExecutionException exception = assertThrows(ExecutionException.class, futureResponse::get);
        assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getCause().getMessage()).isEqualTo("fileId와 message 필드는 동시에 존재할 수 없습니다.");

        // messageRepository.save()가 호출되지 않았는지 검증
        verify(messageRepository, never()).save(any(MessageEntity.class));
    }

    @Test
    @DisplayName("update 시 StatusReadMessageNotFound 조회를 못하면 testUpdateStatusReadMessageNotFound을 던진다.")
    public void testUpdateStatusReadMessageNotFound() {
        // messageRepository.findById()가 빈 Optional을 반환하도록 설정
        String conversationId = IdGenerator.getGenerateConversationId();
        when(messageRepository.findById(conversationId)).thenReturn(java.util.Optional.empty());

        // 비동기 메서드 호출
        CompletableFuture<ConversationMessageStatusResponse> futureResponse = messageService.updateStatusRead(conversationId, "MOD_001");

        // 예외가 발생해야 하므로, CompletableFuture가 예외를 담고 있는지 확인
        ExecutionException exception = assertThrows(ExecutionException.class, futureResponse::get);
        assertThat(exception.getCause()).isInstanceOf(EntityNotFoundException.class);
        assertThat(exception.getCause().getMessage()).isEqualTo("Message not found");

        // messageRepository.updateMessageStatus()가 호출되었는지 검증
        verify(messageRepository, times(1)).updateMessageStatus(conversationId, "MOD_001", MessageStatus.READ);
        verify(messageRepository, times(1)).findById(conversationId);
    }
}