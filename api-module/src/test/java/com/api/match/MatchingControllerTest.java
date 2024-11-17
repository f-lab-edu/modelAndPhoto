package com.api.match;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchingController.class)
class MatchingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("매칭 가능한 사용자 목록 조회")
    public void testGetMatchingUsers() throws Exception {
        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(get("/api/v1/matchings")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matches").exists())
                .andExpect(jsonPath("$.matches[0].userId").exists())
                .andExpect(jsonPath("$.matches[0].role").exists())
                .andExpect(jsonPath("$.matches[0].location").exists())
                .andExpect(jsonPath("$.matches[0].style").exists())
                .andExpect(jsonPath("$.matches[0].introduce").exists());
    }

    @Test
    @DisplayName("매칭 요청 전송")
    public void testSendMatchRequest() throws Exception {

        String requestBody = "{\"sender_id\":123, \"receiver_id\":456}";

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(post("/api/v1/matchings/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchRequestId").exists())
                .andExpect(jsonPath("$.status").value("pending"));
    }


    @Test
    @DisplayName("매칭 요청 목록 조회 (GET)")
    public void testGetMatchRequests() throws Exception {
        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(get("/api/v1/matchings/requests")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requests[0].requestId").exists())
                .andExpect(jsonPath("$.requests[0].senderName").exists())
                .andExpect(jsonPath("$.requests[0].senderRole").exists())
                .andExpect(jsonPath("$.requests[0].status").exists());
    }


    @Test
    @DisplayName("매칭 요청 수락 또는 거절")
    public void testRespondToMatchRequest() throws Exception {

        String responseStatus = "accepted";
        String requestBody = "{\"request_id\":\"req_123\", \"response\":\"" + responseStatus + "\"}";

        // MockMvc를 사용하여 요청을 보냄
        mockMvc.perform(post("/api/v1/matchings/respond")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(responseStatus));
    }
}