package com.api.conversation;

import com.api.dto.conversation.ConversationDto;
import com.api.dto.message.MessageDto;
import com.api.enums.MessageStatus;
import com.api.service.ConversationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConversationService conversationService;

    @Test
    @DisplayName("사용자간 대화목록 조회")
    public void testGetUserConversations() throws Exception {

        // Mock Service behavior
        when(conversationService.retrieve(any(String.class))).thenReturn(
                List.of(new ConversationDto("CON_001", List.of("MOD_001", "PHO_001"), LocalDateTime.now())));

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(get("/api/v1/messages/conversations")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conversationDtos[0].conversationId").exists())
                .andExpect(jsonPath("$.conversationDtos[0].participantIds[0]").exists())
                .andExpect(jsonPath("$.conversationDtos[0].lastMessageTimestamp").exists());
    }


    @Test
    @DisplayName("대화 내 메시지 조회 (시간별 range 조회)")
    public void testGetMessagesInConversation() throws Exception {

        // Mock Service behavior
        when(conversationService.retrieveMessageInConversation(any(String.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(new MessageDto("MSG_001", "MOD_001", "PHO_001", "안녕하세요!" ,LocalDateTime.now(), MessageStatus.READ)));

        // MockMvc를 사용하여 요청을 보냄
        String conversationId = "CON_123";

        mockMvc.perform(get("/api/v1/messages/conversations/" + conversationId)
                        .param("start", "2024-11-01T00:00:00")
                        .param("end", "2024-11-10T23:59:59")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conversationId").value(conversationId))
                .andExpect(jsonPath("$.messageDtos[0].messageId").exists())
                .andExpect(jsonPath("$.messageDtos[0].senderId").exists())
                .andExpect(jsonPath("$.messageDtos[0].receiverId").exists())
                .andExpect(jsonPath("$.messageDtos[0].messageContent").exists())
                .andExpect(jsonPath("$.messageDtos[0].readStatus").exists());
    }
}