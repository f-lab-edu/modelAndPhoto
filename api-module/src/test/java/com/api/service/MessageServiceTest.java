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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
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
    void test_send() {
        // given
        String generatedConversationID = IdGenerator.getGenerateConversationId();
        MessageRequest messageRequest = new MessageRequest("MOD_123", "PHO_456", generatedConversationID, "메시지내용", null);

        doReturn(new MessageEntity("MSG_001", generatedConversationID, "MOD_123", "PHO_456", null, "메시지내용", LocalDateTime.now(), MessageStatus.SENT))
                .when(messageRepository).save(any(MessageEntity.class));

        // when
        MessageResponse messageResponse = messageService.send(messageRequest);

        // then
        assertThat(messageResponse.getStatus()).isEqualTo(MessageStatus.SENT);
        assertThat(messageResponse.getConversationId()).isEqualTo(generatedConversationID);

        // verify
        verify(messageRepository, times(1)).save(any(MessageEntity.class));
    }

    @Test
    @DisplayName("메시지 messageRequest에 fileId와 message 필드가 동시에 존재하면 IllegalArgumentException을 던진다.")
    void test_message_request_file_id_and_message_content_not_exist_at_the_same_time() {
        // given
        MessageRequest messageRequest = new MessageRequest("MOD_123", "PHO_456", "CON_001", "메시지내용", "FIL_123");

        // then
        assertThatIllegalArgumentException().isThrownBy(() -> messageService.send(messageRequest));
    }

    @Test
    @DisplayName("메시지 읽음 처리")
    void test_update_status_read() {
        // given
        String conversationId = "CON_123";
        String readerId = "PHO_456";

        doReturn(Optional.of(new MessageEntity("MSG_001", conversationId, "MOD_123", readerId, null, "메시지내용", LocalDateTime.now(), MessageStatus.READ))).when(messageRepository).findById(any(String.class));

        // when
        ConversationMessageStatusResponse conversationMessageStatusResponse = messageService.updateStatusRead(conversationId, readerId);

        // then
        assertThat(conversationMessageStatusResponse.getConversationId()).isEqualTo(conversationId);
        assertThat(conversationMessageStatusResponse.getMessageStatus()).isEqualTo(MessageStatus.READ);

        // verify
        verify(messageRepository, times(1)).updateMessageStatus(any(String.class), any(String.class), any(MessageStatus.class));
    }
}