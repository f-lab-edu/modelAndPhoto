package com.api.message;

import com.api.dto.message.*;
import com.api.enums.MessageStatus;
import com.api.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Test
    @DisplayName("메시지 전송")
    public void testSendMessage() throws Exception {
        // 요청 (JSON)
        String senderId = "MOD_123";
        String receiverId = "PHO_456";
        String conversationId = "COV_001";

        MessageRequestDto requestDto = new MessageRequestDto(senderId, receiverId, conversationId, "안녕하세요!", "");

        MessageResponse response = new MessageResponse("MSG_001", "CON_001", senderId, receiverId, LocalDateTime.now(), MessageStatus.SENT);

        when(messageService.send(any(MessageRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(post("/api/v1/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(requestDto)))
                .andDo(print())
                .andExpect(request().asyncStarted()) // 비동기 시작 확인
                .andReturn()
                .getAsyncResult(); // 비동기 결과 기다림

        // 비동기 작업 완료 후 결과 검증
        mockMvc.perform(asyncDispatch(mockMvc.perform(post("/api/v1/messages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsBytes(requestDto)))
                        .andExpect(request().asyncStarted())
                        .andReturn()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value("MSG_001"))
                .andExpect(jsonPath("$.conversationId").value("CON_001"))
                .andExpect(jsonPath("$.senderId").value("MOD_123"))
                .andExpect(jsonPath("$.receiverId").value("PHO_456"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value("SENT"));
    }

    @Test
    @DisplayName("대화방 메시지 읽음 상태 업데이트")
    public void testUpdateReadStatus() throws Exception {
        String conversationId = "CON_001";
        ReadStatusUpdateRequest readStatusUpdateRequest = new ReadStatusUpdateRequest(conversationId);

        ConversationMessageStatusResponse conversationMessageStatusResponse = new ConversationMessageStatusResponse(conversationId, MessageStatus.READ);
        when(messageService.updateStatusRead(any(String.class), any(String.class)))
                .thenReturn(CompletableFuture.completedFuture(conversationMessageStatusResponse));

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(post("/api/v1/messages/conversations/" + conversationId + "/read")
                        .contentType(MediaType.APPLICATION_JSON)  // 요청의 Content-Type 설정
                        .content(new ObjectMapper().writeValueAsBytes(readStatusUpdateRequest)))                    // 요청 본문 설정
                .andDo(print())                                   // 요청과 응답을 콘솔에 출력
                .andExpect(status().isOk())                       // 응답 상태 코드가 200 OK인지 확인
                .andExpect(jsonPath("$.conversationId").value(conversationId))      // 응답 JSON에서 conversation_id 확인
                .andExpect(jsonPath("$.status").value(MessageStatus.READ.name()))  // 응답 JSON에서 status 확인
                .andExpect(jsonPath("$.timestamp").exists());                // 응답 JSON에서 timestamp가 있는지 확인
    }
}