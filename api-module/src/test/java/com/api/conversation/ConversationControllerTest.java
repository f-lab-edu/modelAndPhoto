package com.api.conversation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConversationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("사용자간 대화목록 조회")
    public void testGetUserConversations() throws Exception {

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(get("/api/v1/messages/conversations")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conversations[0].conversationId").exists())
                .andExpect(jsonPath("$.conversations[0].participantIds[0]").exists())
                .andExpect(jsonPath("$.conversations[0].lastMessageTimestamp").exists());
    }


    @Test
    @DisplayName("대화 내 메시지 조회 (시간별 range 조회)")
    public void testGetMessagesInConversation() throws Exception {

        // MockMvc를 사용하여 요청을 보냄
        String convId = "conv_123";

        mockMvc.perform(get("/api/v1/messages/conversations/" + convId)
                        .param("start", "2024-11-01T00:00:00")
                        .param("end", "2024-11-10T23:59:59")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conversationId").value(convId))
                .andExpect(jsonPath("$.messages[0].messageId").exists())
                .andExpect(jsonPath("$.messages[0].senderId").exists())
                .andExpect(jsonPath("$.messages[0].receiverId").exists())
                .andExpect(jsonPath("$.messages[0].messageContent").exists())
                .andExpect(jsonPath("$.messages[0].readStatus").isBoolean());
    }
}